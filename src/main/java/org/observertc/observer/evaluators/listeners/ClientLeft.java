package org.observertc.observer.evaluators.listeners;

import io.micronaut.context.annotation.Prototype;
import org.observertc.observer.common.UUIDAdapter;
import org.observertc.observer.configs.ObserverConfig;
import org.observertc.observer.dto.ClientDTO;
import org.observertc.observer.evaluators.listeners.attachments.ClientAttachment;
import org.observertc.observer.events.CallEventType;
import org.observertc.observer.repositories.RepositoryEvents;
import org.observertc.observer.repositories.RepositoryExpiredEvent;
import org.observertc.observer.repositories.tasks.FetchCallClientsTask;
import org.observertc.observer.repositories.tasks.RemoveCallsTask;
import org.observertc.observer.repositories.tasks.RemoveClientsTask;
import org.observertc.schemas.reports.CallEventReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Provider;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Prototype
class ClientLeft extends EventReporterAbstract.CallEventReporterAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ClientLeft.class);

    @Inject
    Provider<RemoveClientsTask> removeClientsTaskProvider;

    @Inject
    Provider<FetchCallClientsTask> fetchCallClientsTaskProvider;

    @Inject
    Provider<RemoveCallsTask> removeCallsTaskProvider;

    @Inject
    ObserverConfig observerConfig;

    @Inject
    RepositoryEvents repositoryEvents;

    @PostConstruct
    void setup() {
        this.repositoryEvents
                .removedClients()
                .subscribe(this::receiveRemovedClients);
        this.repositoryEvents
                .expiredClients()
                .subscribe(this::receiveExpiredClients);
    }

    private void receiveRemovedClients(List<ClientDTO> clientDTOs) {
        if (Objects.isNull(clientDTOs) || clientDTOs.size() < 1) {
            return;
        }

        var reports = clientDTOs.stream()
                .map(this::makeReport)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.forward(reports);
    }

    private void receiveExpiredClients(List<RepositoryExpiredEvent<ClientDTO>> expiredClientDTOs) {
        if (Objects.isNull(expiredClientDTOs) || expiredClientDTOs.size() < 1) {
            return;
        }
        var removeClientsTask = removeClientsTaskProvider.get();
        expiredClientDTOs.stream()
                .map(expiredDTO -> expiredDTO.getValue())
                .filter(Objects::nonNull)
                .forEach(removeClientsTask::addRemovedClientDTO);

        if (!removeClientsTask.execute().succeeded()) {
            logger.warn("Remove Client Entities are failed");
            return;
        }
        var affectedCallIds = new HashSet<UUID>();
        var reports = expiredClientDTOs.stream()
                .filter(Objects::nonNull)
                .map(expiredClientDTO -> {
                    var timestamp = expiredClientDTO.estimatedLastTouch();
                    var clientDTO = expiredClientDTO.getValue();
                    if (Objects.isNull(clientDTO) || Objects.isNull(clientDTO.callId)) {
                        return null;
                    }
                    affectedCallIds.add(clientDTO.callId);
                    var report = this.makeReport(clientDTO, timestamp);
                    return report;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.forward(reports);
        this.removeAbandonedCallIds(affectedCallIds);
    }

    private CallEventReport makeReport(ClientDTO clientDTO) {
        Long now = Instant.now().toEpochMilli();
        return this.makeReport(clientDTO, now);
    }

    private void removeAbandonedCallIds(Set<UUID> affectedCallIds) {
        var fetchCallsClientIds = fetchCallClientsTaskProvider.get();
        fetchCallsClientIds.whereCallIds(affectedCallIds);
        if (!fetchCallsClientIds.execute().succeeded()) {
            return;
        }
        Set<UUID> abandonedCallIds = new HashSet<>();
        Map<UUID, Set<UUID>> remainedCallClientIds = fetchCallsClientIds.getResult();
        affectedCallIds.stream()
                .forEach(affectedCallId -> {
                    Set<UUID> remainedClientIds = remainedCallClientIds.get(affectedCallId);
                    if (Objects.isNull(remainedClientIds)) {
                        abandonedCallIds.add(affectedCallId);
                    }
                    if (remainedClientIds.size() < 1) {
                        abandonedCallIds.add(affectedCallId);
                    }
                });
        if (abandonedCallIds.size() < 1) {
            return;
        }

        var removeCallsTask = this.removeCallsTaskProvider.get()
                .whereCallIds(abandonedCallIds);

        if (!removeCallsTask.execute().succeeded()) {
            logger.warn("Remove calls task has failed");
        }
    }


    protected CallEventReport makeReport(ClientDTO clientDTO, Long timestamp) {
        try {
            String callId = UUIDAdapter.toStringOrNull(clientDTO.callId);
            String clientId = UUIDAdapter.toStringOrNull(clientDTO.clientId);
            ClientAttachment attachment = ClientAttachment.builder()
                    .withTimeZoneId(clientDTO.timeZoneId)
                    .build();
            var result = CallEventReport.newBuilder()
                    .setName(CallEventType.CLIENT_LEFT.name())
                    .setCallId(callId)
                    .setServiceId(clientDTO.serviceId)
                    .setRoomId(clientDTO.roomId)
                    .setClientId(clientId)
                    .setMediaUnitId(clientDTO.mediaUnitId)
                    .setUserId(clientDTO.userId)
                    .setTimestamp(timestamp)
                    .setAttachments(attachment.toBase64())
                    .build();
            logger.info("Client {} left call \"{}\" in service \"{}\" at room \"{}\"", clientDTO.clientId, clientDTO.callId, clientDTO.serviceId, clientDTO.roomId);
            return result;
        } catch (Exception ex) {
            logger.warn("Unexpected exception occurred while making report", ex);
            return null;
        }
    }
}
