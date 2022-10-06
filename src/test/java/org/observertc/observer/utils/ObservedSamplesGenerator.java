package org.observertc.observer.utils;

import org.observertc.observer.samples.ObservedClientSample;
import org.observertc.observer.samples.ObservedSfuSample;

import java.util.UUID;

public class ObservedSamplesGenerator {

    public static ObservedSamplesGenerator createSharedRoomGenerator(ObservedSamplesGenerator source) {
        var result = new ObservedSamplesGenerator();
        result.serviceId = source.serviceId;
        if (source.roomId == null) {
            source.roomId = result.randomGenerators.getRandomTestRoomIds();
        }
        result.roomId = source.roomId;
        return result;
    }

    private final RandomGenerators randomGenerators = new RandomGenerators();
    private String serviceId = randomGenerators.getRandomServiceId();
    private final String CLIENT_MEDIA_UNIT_ID = randomGenerators.getRandomClientSideMediaUnitId();
    private final String TIME_ZONE_ID = randomGenerators.getRandomTimeZoneId();
    private final ClientSideSamplesGenerator clientSamples;
    private final SfuSideSamplesGenerator sfuSamples;
    private String roomId = null;

    public ObservedSamplesGenerator() {
        Long clientInbAudioSSRC = this.randomGenerators.getRandomSSRC();
        Long clientInbVideoSSRC = this.randomGenerators.getRandomSSRC();
        Long clientOutbAudioSSRC = this.randomGenerators.getRandomSSRC();
        Long clientOutbVideoSSRC = this.randomGenerators.getRandomSSRC();
        var audioStreamId = UUID.randomUUID().toString();
        var videoStreamId = UUID.randomUUID().toString();
        var audioSinkId = UUID.randomUUID().toString();
        var videoSinkId = UUID.randomUUID().toString();
        var peerConnectionId = UUID.randomUUID().toString();
        var transportId = UUID.randomUUID().toString();
        this.clientSamples = new ClientSideSamplesGenerator()
                .setClientId(UUID.randomUUID().toString())
                .setCallId(UUID.randomUUID().toString())
                .setMarker(this.randomGenerators.getRandomString())
                .setRoomId(this.randomGenerators.getRandomTestRoomIds())
                .setUserId(this.randomGenerators.getRandomTestUserIds())
                .setTimeZoneOffsetInHours( -3)
                .addBrowser()
                .addCertificate()
                .addEngine()
                .addPlatform()
                .addExtensionStat()
                .addMediaConstraint("constraint")
                .addPeerConnection(peerConnectionId)
                .addIceLocalCandidate()
                .addIceRemoteCandidate()
                .addOperationSystem()
                .addIceServer("https://IceServer.com")
                .addMediaCodec()
                .addMediaDevice()
                .addMediaSource()
                .addUserMediaError("userMediaError")
                .addLocalSdp("a=candidate:2 1 UDP 1694498815 192.0.2.3 45664 typ srflx raddr 10.0.1.1 rport 8998")
                .addDataChannel(peerConnectionId, UUID.randomUUID().toString())
                .addInboundAudioTrack(peerConnectionId, UUID.randomUUID().toString(), clientInbAudioSSRC, audioStreamId, audioSinkId)
                .addInboundVideoTrack(peerConnectionId, UUID.randomUUID().toString(), clientInbVideoSSRC, videoStreamId, videoSinkId)
                .addOutboundAudioTrack(peerConnectionId, UUID.randomUUID().toString(), clientOutbAudioSSRC, audioStreamId)
                .addOutboundVideoTrack(peerConnectionId, UUID.randomUUID().toString(), clientOutbVideoSSRC, videoStreamId)
                ;

        this.sfuSamples = new SfuSideSamplesGenerator()
                .setSfuId(UUID.randomUUID().toString())
                .setMarker(this.randomGenerators.getRandomString())
                .setTimeZoneOffsetInHours( -2)
                .addTransport(transportId)
                .addDataChannel(transportId, UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .addInboundRtpPad(transportId, UUID.randomUUID().toString(), clientOutbAudioSSRC, audioStreamId)
                .addInboundRtpPad(transportId, UUID.randomUUID().toString(), clientOutbVideoSSRC, videoStreamId)
                .addOutboundRtpPad(transportId, UUID.randomUUID().toString(), clientInbAudioSSRC, audioStreamId, audioSinkId)
                .addOutboundRtpPad(transportId, UUID.randomUUID().toString(), clientInbVideoSSRC, videoStreamId, videoSinkId)
                .addExtensionStat()
                ;
    }

    public ObservedClientSample generateObservedClientSample() {
        return generateObservedClientSample(null);
    }

    public ObservedClientSample generateObservedClientSample(String callId) {
        var samples = this.clientSamples.get();
        var clientSample = samples.clientSamples[0];
        clientSample.callId = callId;
        if (this.roomId != null) {
            clientSample.roomId = this.roomId;
        }
        var result = ObservedClientSample.builder()
                .setClientSample(clientSample)
                .setTimeZoneId(TIME_ZONE_ID)
                .setServiceId(serviceId)
                .setMediaUnitId(CLIENT_MEDIA_UNIT_ID)
                .build();
        return result;
    }

    public ObservedSfuSample generateObservedSfuSample() {
        var samples = this.sfuSamples.get();
        var sfuSample = samples.sfuSamples[0];
        var result = ObservedSfuSample.builder()
                .setSfuSample(sfuSample)
                .setTimeZoneId(TIME_ZONE_ID)
                .setServiceId(serviceId)
                .setMediaUnitId(CLIENT_MEDIA_UNIT_ID)
                .build();
        return result;
    }

}
