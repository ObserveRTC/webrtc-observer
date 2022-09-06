package org.observertc.observer.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

public interface ObservedClientSamples extends Iterable<ObservedClientSample> {
    static final Logger logger = LoggerFactory.getLogger(ObservedClientSamples.class);

    static ObservedClientSamples EMPTY_SAMPLES = ObservedClientSamples.builder().build();

    static Builder builder() {
        return new Builder();
    }

    boolean isEmpty();

    Stream<ObservedClientSample> stream();

    int size();

    Set<ServiceRoomId> getServiceRoomIds();

    Set<String> getClientIds();

    Set<String> getPeerConnectionIds();

//    Set<String> getMediaTrackIds();

    Set<String> getInboundTrackIds();
    Set<String> getOutboundTrackIds();

    class Builder {
        private Set<String> clientIds = new HashSet<>();
        private Set<String> peerConnectionIds = new HashSet<>();
        private Set<String> inboundTrackIds = new HashSet<>();
        private Set<String> outboundTrackIds = new HashSet<>();

        private Set<ServiceRoomId> serviceRoomIds = new HashSet<>();
        private List<ObservedClientSample> clientSamples = new LinkedList<>();

        public Builder addObservedClientSample(ObservedClientSample value) {
            var clientSample = value.getClientSample();
            ClientSampleVisitor.streamPeerConnectionTransports(clientSample)
                    .map(transport -> transport.peerConnectionId)
                    .filter(Objects::nonNull)
                    .forEach(peerConnectionIds::add);
            ClientSampleVisitor.streamInboundAudioTracks(clientSample)
                    .map(track -> track.trackId)
                    .filter(Objects::nonNull)
                    .forEach(inboundTrackIds::add);
            ClientSampleVisitor.streamInboundVideoTracks(clientSample)
                    .map(track -> track.trackId)
                    .filter(Objects::nonNull)
                    .forEach(inboundTrackIds::add);
            ClientSampleVisitor.streamOutboundAudioTracks(clientSample)
                    .map(track -> track.trackId)
                    .filter(Objects::nonNull)
                    .forEach(outboundTrackIds::add);
            ClientSampleVisitor.streamOutboundVideoTracks(clientSample)
                    .map(track -> track.trackId)
                    .filter(Objects::nonNull)
                    .forEach(outboundTrackIds::add);
            if (Objects.nonNull(clientSample.clientId)) {
                this.clientIds.add(clientSample.clientId);
            }
            this.serviceRoomIds.add(value.getServiceRoomId());
            this.clientSamples.add(value);
            return this;
        }

//         compile this below if you want to validate all the inputs
//        public Builder addObservedClientSample(ObservedClientSample value) {
//            var clientSample = value.getClientSample();
//            var nullIds = new HashSet<UUID>();
//            var peerConnectionIdFilter = Utils.makeTrash(Objects::nonNull, nullIds);
//            var inboundAudioTrackIdFilter = Utils.makeTrash(Objects::nonNull, nullIds);
//            var inboundVideoTrackIdFilter = Utils.makeTrash(Objects::nonNull, nullIds);
//            var outboundAudioTrackIdFilter = Utils.makeTrash(Objects::nonNull, nullIds);
//            var outboundVideoTrackIdFilter = Utils.makeTrash(Objects::nonNull, nullIds);
//            ClientSampleVisitor.streamPeerConnectionTransports(clientSample)
//                .map(transport -> transport.peerConnectionId)
//                .filter(peerConnectionIdFilter)
//                .forEach(peerConnectionIds::add);
//            ClientSampleVisitor.streamInboundAudioTracks(clientSample)
//                    .map(track -> track.trackId)
//                    .filter(inboundAudioTrackIdFilter)
//                    .forEach(mediaTrackIds::add);
//            ClientSampleVisitor.streamInboundVideoTracks(clientSample)
//                    .map(track -> track.trackId)
//                    .filter(inboundVideoTrackIdFilter)
//                    .forEach(mediaTrackIds::add);
//            ClientSampleVisitor.streamOutboundAudioTracks(clientSample)
//                    .map(track -> track.trackId)
//                    .filter(outboundAudioTrackIdFilter)
//                    .forEach(mediaTrackIds::add);
//            ClientSampleVisitor.streamOutboundVideoTracks(clientSample)
//                    .map(track -> track.trackId)
//                    .filter(outboundVideoTrackIdFilter)
//                    .forEach(mediaTrackIds::add);
//            if (Objects.nonNull(clientSample.clientId)) {
//                this.clientIds.add(clientSample.clientId);
//            }
//            if (0 < nullIds.size()) {
//                logger.warn("Null Identifier is detected {}", JsonUtils.objectToString(clientSample));
//            }
//            this.serviceRoomIds.add(value.getServiceRoomId());
//            this.clientSamples.add(value);
//            return this;
//        }

        public ObservedClientSamples build() {
            return new ObservedClientSamples() {
                @Override
                public Iterator<ObservedClientSample> iterator() {
                    return clientSamples.iterator();
                }

                @Override
                public boolean isEmpty() {
                    return clientSamples.isEmpty();
                }

                @Override
                public Stream<ObservedClientSample> stream() {
                    return clientSamples.stream();
                }

                @Override
                public int size() {
                    return clientSamples.size();
                }

                @Override
                public Set<ServiceRoomId> getServiceRoomIds() {
                    return serviceRoomIds;
                }

                @Override
                public Set<String> getClientIds() {
                    return clientIds;
                }

                @Override
                public Set<String> getPeerConnectionIds() {
                    return peerConnectionIds;
                }

                @Override
                public Set<String> getInboundTrackIds() {
                    return inboundTrackIds;
                }

                @Override
                public Set<String> getOutboundTrackIds() {
                    return outboundTrackIds;
                }


            };
        }

    }
}
