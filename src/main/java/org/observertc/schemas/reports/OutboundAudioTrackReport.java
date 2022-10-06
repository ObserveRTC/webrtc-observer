package org.observertc.schemas.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* A Report created for Outbound Audio Tracks. A combination of Audio source, Codec metadata carrying outbound and remote inbound RTP stat measurements
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundAudioTrackReport {
	public static final String VERSION="2.1.8";
	public static Builder newBuilder() {
		return new Builder();
	}
	/**
	* The unique identifier of the service
	*/
	@JsonProperty("serviceId")
	public String serviceId;
	/**
	* The media unit id the report belongs to
	*/
	@JsonProperty("mediaUnitId")
	public String mediaUnitId;
	/**
	* The marker the originated sample is reported with
	*/
	@JsonProperty("marker")
	public String marker;
	/**
	* The timestamp when the corresponded data is generated for the report (UTC Epoch in ms)
	*/
	@JsonProperty("timestamp")
	public Long timestamp;
	/**
	* The generated unique identifier of the call
	*/
	@JsonProperty("callId")
	public String callId;
	/**
	* webrtc app provided room id
	*/
	@JsonProperty("roomId")
	public String roomId;
	/**
	* The generated unique identifier of the client
	*/
	@JsonProperty("clientId")
	public String clientId;
	/**
	* webrtc app provided user identifier
	*/
	@JsonProperty("userId")
	public String userId;
	/**
	* The unique identifier of the peer connection
	*/
	@JsonProperty("peerConnectionId")
	public String peerConnectionId;
	/**
	* The webrtc app provided label the peer connection is labeled with
	*/
	@JsonProperty("label")
	public String label;
	/**
	* The id of the track
	*/
	@JsonProperty("trackId")
	public String trackId;
	/**
	* The id of the Sfu stream corresponds to the outbound track
	*/
	@JsonProperty("sfuStreamId")
	public String sfuStreamId;
	/**
	* The sequence number of the sample the report is generated from
	*/
	@JsonProperty("sampleSeq")
	public Integer sampleSeq;
	/**
	* The RTP SSRC field
	*/
	@JsonProperty("ssrc")
	public Long ssrc;
	/**
	* The total number of packets sent on the corresponded synchronization source
	*/
	@JsonProperty("packetsSent")
	public Integer packetsSent;
	/**
	* The total number of bytes sent on the corresponded synchronization source
	*/
	@JsonProperty("bytesSent")
	public Long bytesSent;
	/**
	*  The rid encoding parameter of the corresponded synchronization source
	*/
	@JsonProperty("rid")
	public String rid;
	/**
	* Total number of RTP header and padding bytes sent over the corresponding synchronization source (ssrc)
	*/
	@JsonProperty("headerBytesSent")
	public Long headerBytesSent;
	/**
	* Total number of retransmitted packets sent over the corresponding synchronization source (ssrc).
	*/
	@JsonProperty("retransmittedPacketsSent")
	public Integer retransmittedPacketsSent;
	/**
	* Total number of retransmitted bytes sent over the corresponding synchronization source (ssrc).
	*/
	@JsonProperty("retransmittedBytesSent")
	public Long retransmittedBytesSent;
	/**
	* Reflects the current encoder target in bits per second.
	*/
	@JsonProperty("targetBitrate")
	public Integer targetBitrate;
	/**
	* The total number of bytes of RTP coherent frames encoded completly depending on the frame size the encoder targets
	*/
	@JsonProperty("totalEncodedBytesTarget")
	public Long totalEncodedBytesTarget;
	/**
	* The total number of delay packets buffered at the sender side in seconds over the corresponding synchronization source
	*/
	@JsonProperty("totalPacketSendDelay")
	public Double totalPacketSendDelay;
	/**
	* The average RTCP interval between two consecutive compound RTCP packets sent for the corresponding synchronization source (ssrc)
	*/
	@JsonProperty("averageRtcpInterval")
	public Double averageRtcpInterval;
	/**
	* Count the total number of Negative ACKnowledgement (NACK) packets received over the corresponding synchronization source (ssrc)
	*/
	@JsonProperty("nackCount")
	public Integer nackCount;
	/**
	* Indicate the name of the encoder implementation library
	*/
	@JsonProperty("encoderImplementation")
	public String encoderImplementation;
	/**
	* Indicates whether this RTP stream is configured to be sent or disabled
	*/
	@JsonProperty("active")
	public Boolean active;
	/**
	* The total number of packets received on the corresponded synchronization source
	*/
	@JsonProperty("packetsReceived")
	public Integer packetsReceived;
	/**
	* The total number of bytes received on the corresponded synchronization source
	*/
	@JsonProperty("packetsLost")
	public Integer packetsLost;
	/**
	* The corresponded synchronization source reported jitter
	*/
	@JsonProperty("jitter")
	public Double jitter;
	/**
	* RTT measurement in seconds based on (most likely) SR, and RR belongs to the corresponded synchronization source
	*/
	@JsonProperty("roundTripTime")
	public Double roundTripTime;
	/**
	* The sum of RTT measurements belongs to the corresponded synchronization source
	*/
	@JsonProperty("totalRoundTripTime")
	public Double totalRoundTripTime;
	/**
	* The receiver reported fractional lost belongs to the corresponded synchronization source
	*/
	@JsonProperty("fractionLost")
	public Double fractionLost;
	/**
	* The total number of calculated RR measurements received on this source
	*/
	@JsonProperty("roundTripTimeMeasurements")
	public Integer roundTripTimeMeasurements;
	/**
	* True if the corresponded media source is remote, false otherwise (or null depending on browser and version)
	*/
	@JsonProperty("relayedSource")
	public Boolean relayedSource;
	/**
	* Represents the audio level reported by the media source
	*/
	@JsonProperty("audioLevel")
	public Double audioLevel;
	/**
	* Represents the energy level reported by the media source
	*/
	@JsonProperty("totalAudioEnergy")
	public Double totalAudioEnergy;
	/**
	* Represents the total duration of the audio samples the media source actually transconverted in seconds
	*/
	@JsonProperty("totalSamplesDuration")
	public Double totalSamplesDuration;
	/**
	* Represents the echo cancellation in decibels corresponded to the media source.
	*/
	@JsonProperty("echoReturnLoss")
	public Double echoReturnLoss;
	/**
	* Represents the echo cancellation in decibels added as a postprocessing by the library after the audio is catched from the emdia source.
	*/
	@JsonProperty("echoReturnLossEnhancement")
	public Double echoReturnLossEnhancement;
	/**
	* . The total duration, in seconds, of samples produced by the device that got dropped before reaching the media source
	*/
	@JsonProperty("droppedSamplesDuration")
	public Double droppedSamplesDuration;
	/**
	* A counter increases every time a sample is dropped after a non-dropped sample
	*/
	@JsonProperty("droppedSamplesEvents")
	public Integer droppedSamplesEvents;
	/**
	* Total delay, in seconds, for each audio sample between the time the sample was emitted by the capture device and the sample reaching the source
	*/
	@JsonProperty("totalCaptureDelay")
	public Double totalCaptureDelay;
	/**
	* The total number of captured samples reaching the audio source
	*/
	@JsonProperty("totalSamplesCaptured")
	public Double totalSamplesCaptured;


	public static class Builder {

		private OutboundAudioTrackReport result = new OutboundAudioTrackReport();

		public Builder setServiceId(String value) {
			this.result.serviceId = value;
			return this;
		}
		public Builder setMediaUnitId(String value) {
			this.result.mediaUnitId = value;
			return this;
		}
		public Builder setMarker(String value) {
			this.result.marker = value;
			return this;
		}
		public Builder setTimestamp(Long value) {
			this.result.timestamp = value;
			return this;
		}
		public Builder setCallId(String value) {
			this.result.callId = value;
			return this;
		}
		public Builder setRoomId(String value) {
			this.result.roomId = value;
			return this;
		}
		public Builder setClientId(String value) {
			this.result.clientId = value;
			return this;
		}
		public Builder setUserId(String value) {
			this.result.userId = value;
			return this;
		}
		public Builder setPeerConnectionId(String value) {
			this.result.peerConnectionId = value;
			return this;
		}
		public Builder setLabel(String value) {
			this.result.label = value;
			return this;
		}
		public Builder setTrackId(String value) {
			this.result.trackId = value;
			return this;
		}
		public Builder setSfuStreamId(String value) {
			this.result.sfuStreamId = value;
			return this;
		}
		public Builder setSampleSeq(Integer value) {
			this.result.sampleSeq = value;
			return this;
		}
		public Builder setSsrc(Long value) {
			this.result.ssrc = value;
			return this;
		}
		public Builder setPacketsSent(Integer value) {
			this.result.packetsSent = value;
			return this;
		}
		public Builder setBytesSent(Long value) {
			this.result.bytesSent = value;
			return this;
		}
		public Builder setRid(String value) {
			this.result.rid = value;
			return this;
		}
		public Builder setHeaderBytesSent(Long value) {
			this.result.headerBytesSent = value;
			return this;
		}
		public Builder setRetransmittedPacketsSent(Integer value) {
			this.result.retransmittedPacketsSent = value;
			return this;
		}
		public Builder setRetransmittedBytesSent(Long value) {
			this.result.retransmittedBytesSent = value;
			return this;
		}
		public Builder setTargetBitrate(Integer value) {
			this.result.targetBitrate = value;
			return this;
		}
		public Builder setTotalEncodedBytesTarget(Long value) {
			this.result.totalEncodedBytesTarget = value;
			return this;
		}
		public Builder setTotalPacketSendDelay(Double value) {
			this.result.totalPacketSendDelay = value;
			return this;
		}
		public Builder setAverageRtcpInterval(Double value) {
			this.result.averageRtcpInterval = value;
			return this;
		}
		public Builder setNackCount(Integer value) {
			this.result.nackCount = value;
			return this;
		}
		public Builder setEncoderImplementation(String value) {
			this.result.encoderImplementation = value;
			return this;
		}
		public Builder setActive(Boolean value) {
			this.result.active = value;
			return this;
		}
		public Builder setPacketsReceived(Integer value) {
			this.result.packetsReceived = value;
			return this;
		}
		public Builder setPacketsLost(Integer value) {
			this.result.packetsLost = value;
			return this;
		}
		public Builder setJitter(Double value) {
			this.result.jitter = value;
			return this;
		}
		public Builder setRoundTripTime(Double value) {
			this.result.roundTripTime = value;
			return this;
		}
		public Builder setTotalRoundTripTime(Double value) {
			this.result.totalRoundTripTime = value;
			return this;
		}
		public Builder setFractionLost(Double value) {
			this.result.fractionLost = value;
			return this;
		}
		public Builder setRoundTripTimeMeasurements(Integer value) {
			this.result.roundTripTimeMeasurements = value;
			return this;
		}
		public Builder setRelayedSource(Boolean value) {
			this.result.relayedSource = value;
			return this;
		}
		public Builder setAudioLevel(Double value) {
			this.result.audioLevel = value;
			return this;
		}
		public Builder setTotalAudioEnergy(Double value) {
			this.result.totalAudioEnergy = value;
			return this;
		}
		public Builder setTotalSamplesDuration(Double value) {
			this.result.totalSamplesDuration = value;
			return this;
		}
		public Builder setEchoReturnLoss(Double value) {
			this.result.echoReturnLoss = value;
			return this;
		}
		public Builder setEchoReturnLossEnhancement(Double value) {
			this.result.echoReturnLossEnhancement = value;
			return this;
		}
		public Builder setDroppedSamplesDuration(Double value) {
			this.result.droppedSamplesDuration = value;
			return this;
		}
		public Builder setDroppedSamplesEvents(Integer value) {
			this.result.droppedSamplesEvents = value;
			return this;
		}
		public Builder setTotalCaptureDelay(Double value) {
			this.result.totalCaptureDelay = value;
			return this;
		}
		public Builder setTotalSamplesCaptured(Double value) {
			this.result.totalSamplesCaptured = value;
			return this;
		}
		public OutboundAudioTrackReport build() {
			return this.result;
		}
	}
}