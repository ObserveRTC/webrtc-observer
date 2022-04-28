package org.observertc.observer.repositories.tasks;

import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import org.observertc.observer.common.ChainedTask;
import org.observertc.observer.common.Utils;
import org.observertc.observer.dto.ClientDTO;
import org.observertc.observer.dto.MediaTrackDTO;
import org.observertc.observer.micrometer.ExposedMetrics;
import org.observertc.observer.repositories.HazelcastMaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Prototype
public class RefreshCallsTask extends ChainedTask<RefreshCallsTask.Report> {

    private static final Logger logger = LoggerFactory.getLogger(RefreshCallsTask.class);

    public static class Report {
        public Set<UUID> foundClientIds = new HashSet<>();
        public Set<UUID> foundTrackIds = new HashSet<>();
        public Set<UUID> foundPeerConnectionIds = new HashSet<>();
    }


    private Set<UUID> clientIds = new HashSet<>();
    private Set<UUID> peerConnectionIds = new HashSet<>();
    private Set<UUID> mediaTrackIds = new HashSet<>();
    private final Report report = new Report();

    @Inject
    HazelcastMaps hazelcastMaps;

    @Inject
    ExposedMetrics exposedMetrics;

    @PostConstruct
    void setup() {
        this.withStatsConsumer(this.exposedMetrics::processTaskStats);
        new Builder<Report>(this)
                .addActionStage("Check Clients",
                        // action
                        () -> {
                            var nullIds = new HashSet<UUID>();
                            var clientIds = Utils.trash(this.clientIds.stream(), Objects::nonNull, nullIds).collect(Collectors.toSet());
                            if (0 < nullIds.size()) {
                                logger.warn("There were null peer connection ids.");
                            }
                            if (clientIds.size() < 1) {
                                return;
                            }
                            Map<UUID, ClientDTO> clientDTOs = this.hazelcastMaps.getClients().getAll(clientIds);
                            this.report.foundClientIds.addAll(clientDTOs.keySet());
                        })
                .addActionStage("Check Peer Connections",
                        // action
                        () -> {
                            var nullIds = new HashSet<UUID>();
                            var peerConnectionIds = Utils.trash(this.peerConnectionIds.stream(), Objects::nonNull, nullIds).collect(Collectors.toSet());
                            if (0 < nullIds.size()) {
                                logger.warn("There were null peer connection ids.");
                            }
                            if (peerConnectionIds.size() < 1) {
                                return;
                            }
                            var peerConnectionDTOs = this.hazelcastMaps.getPeerConnections().getAll(peerConnectionIds);
                            this.report.foundPeerConnectionIds.addAll(peerConnectionDTOs.keySet());
                        })
                .addActionStage("Check Media Tracks",
                        // action
                        () -> {
                            var nullIds = new HashSet<UUID>();
                            var mediaTrackIds = Utils.trash(this.mediaTrackIds.stream(), Objects::nonNull, nullIds).collect(Collectors.toSet());
                            if (0 < nullIds.size()) {
                                logger.warn("There were null media track ids.");
                            }
                            if (mediaTrackIds.size() < 1) {
                                return;
                            }
                            Map<UUID, MediaTrackDTO> mediaTrackDTOs = this.hazelcastMaps.getMediaTracks().getAll(mediaTrackIds);
                            this.report.foundTrackIds.addAll(mediaTrackDTOs.keySet());
                        })
                .<Report> addTerminalSupplier("Provide the composed report", () -> {
                    return this.report;
                })
                .build();
    }




    public RefreshCallsTask withClientIds(Set<UUID> clientIds) {
        if (Objects.isNull(clientIds)) {
            return this;
        }
        this.clientIds.addAll(clientIds);
        return this;
    }


    public RefreshCallsTask withPeerConnectionIds(Set<UUID> peerConnectionIds) {
        if (Objects.isNull(peerConnectionIds)) {
            return this;
        }
        this.peerConnectionIds.addAll(peerConnectionIds);
        return this;
    }


    public RefreshCallsTask withMediaTrackIds(Set<UUID> mediaTrackIds) {
        if (Objects.isNull(mediaTrackIds)) {
            return this;
        }
        this.mediaTrackIds.addAll(mediaTrackIds);
        return this;
    }

}
