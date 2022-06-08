package org.observertc.schemas.reports.csvsupport;

import java.util.LinkedList;
import org.observertc.observer.reports.Report;
import java.util.function.Function;
import org.observertc.schemas.reports.OutboundVideoTrackReport;

public class OutboundVideoTrackReportToIterable implements Function<Report, Iterable<?>> {
	@Override
	public Iterable<?> apply(Report report) {
		var result = new LinkedList();
		var payload = (OutboundVideoTrackReport) report.payload;
		result.add(payload.serviceId);
		result.add(payload.mediaUnitId);
		result.add(payload.timestamp);
		result.add(payload.callId);
		result.add(payload.clientId);
		result.add(payload.peerConnectionId);
		result.add(payload.sampleSeq);
		result.add(payload.ssrc);
		result.add(payload.averageRtcpInterval);
		result.add(payload.burstDiscardCount);
		result.add(payload.burstDiscardRate);
		result.add(payload.burstLossCount);
		result.add(payload.burstLossRate);
		result.add(payload.burstPacketsDiscarded);
		result.add(payload.burstPacketsLost);
		result.add(payload.bytesDiscardedOnSend);
		result.add(payload.bytesSent);
		result.add(payload.channels);
		result.add(payload.clockRate);
		result.add(payload.encodedFrameBitDepth);
		result.add(payload.encodedFrameHeight);
		result.add(payload.encodedFramesPerSecond);
		result.add(payload.encodedFrameWidth);
		result.add(payload.encoderImplementation);
		result.add(payload.ended);
		result.add(payload.fecPacketsSent);
		result.add(payload.firCount);
		result.add(payload.fractionLost);
		result.add(payload.frameBitDepth);
		result.add(payload.frameHeight);
		result.add(payload.framesDiscardedOnSend);
		result.add(payload.framesDropped);
		result.add(payload.framesEncoded);
		result.add(payload.framesPerSecond);
		result.add(payload.framesSent);
		result.add(payload.frameWidth);
		result.add(payload.fullFramesLost);
		result.add(payload.gapDiscardRate);
		result.add(payload.gapLossRate);
		result.add(payload.headerBytesSent);
		result.add(payload.hugeFramesSent);
		result.add(payload.jitter);
		result.add(payload.keyFramesEncoded);
		result.add(payload.label);
		result.add(payload.lastPacketSentTimestamp);
		result.add(payload.marker);
		result.add(payload.mimeType);
		result.add(payload.nackCount);
		result.add(payload.packetsDiscarded);
		result.add(payload.packetsDiscardedOnSend);
		result.add(payload.packetsLost);
		result.add(payload.packetsReceived);
		result.add(payload.packetsRepaired);
		result.add(payload.packetsSent);
		result.add(payload.partialFramesLost);
		result.add(payload.payloadType);
		result.add(payload.perDscpPacketsSent);
		result.add(payload.pliCount);
		result.add(payload.qpSum);
		result.add(payload.qualityLimitationDurationBandwidth);
		result.add(payload.qualityLimitationDurationCPU);
		result.add(payload.qualityLimitationDurationNone);
		result.add(payload.qualityLimitationDurationOther);
		result.add(payload.qualityLimitationReason);
		result.add(payload.qualityLimitationResolutionChanges);
		result.add(payload.relayedSource);
		result.add(payload.reportsReceived);
		result.add(payload.retransmittedBytesSent);
		result.add(payload.retransmittedPacketsSent);
		result.add(payload.rid);
		result.add(payload.roomId);
		result.add(payload.roundTripTime);
		result.add(payload.roundTripTimeMeasurements);
		result.add(payload.sdpFmtpLine);
		result.add(payload.sfuStreamId);
		result.add(payload.sliCount);
		result.add(payload.targetBitrate);
		result.add(payload.totalEncodedBytesTarget);
		result.add(payload.totalEncodeTime);
		result.add(payload.totalPacketSendDelay);
		result.add(payload.totalRoundTripTime);
		result.add(payload.trackId);
		result.add(payload.userId);

		return result;
	}
}