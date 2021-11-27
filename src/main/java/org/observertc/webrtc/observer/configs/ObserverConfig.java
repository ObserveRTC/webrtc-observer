/*
 * Copyright  2020 Balazs Kreith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.observertc.webrtc.observer.configs;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("observer")
public class ObserverConfig {

	// Security Configurations
	public SecurityConfig security;

	@ConfigurationProperties("security")
	public static class SecurityConfig {

		public boolean allowExposeConfig = false;

		public SecurityConfig.WebsocketSecurityConfig websockets = new SecurityConfig.WebsocketSecurityConfig();


        @ConfigurationProperties("websockets")
		public static class WebsocketSecurityConfig {
			public int expirationInMin = 0; // 0 means the access token provided is used
		}
	}

	public ObfuscationsConfig obfuscations;

	@ConfigurationProperties("obfuscations")
	public static class ObfuscationsConfig {
		public enum ObfuscationType {
			ANONYMIZATION,
			NONE,
		}
		public boolean enabled = false;
		public ObfuscationsMaskConfig maskConfig;

		public ObfuscationType maskedIceAddresses = ObfuscationType.ANONYMIZATION;
		public ObfuscationType maskedUserId = ObfuscationType.ANONYMIZATION;
		public ObfuscationType maskedRoomId = ObfuscationType.ANONYMIZATION;

		@ConfigurationProperties("anonymization")
		public static class ObfuscationsMaskConfig {
			public String hashAlgorithm;
			public String salt;
		}
	}

	// Sources Config
	public SourcesConfig sources;

	@ConfigurationProperties("sources")
	public static class SourcesConfig {

		public List<String> allowedServiceIds = null;

		public RestApiConfig restapi;

		@ConfigurationProperties("restapi")
		public static class RestApiConfig {
			public boolean acceptClientSamples = true;
			public boolean acceptSfuSamples = true;

			@Min(1)
			public int maxClientSamplesBatch = 1000;

			@Min(1)
			public int maxSfuSamplesBatch = 1000;
		}

		public WebsocketsConfig websockets;

		@ConfigurationProperties("websockets")
		public static class WebsocketsConfig {
			public boolean enabled = true;

			@Min(1)
			public int maxClientSamplesBatch = 1000;

			@Min(1)
			public int maxSfuSamplesBatch = 1000;
		}
	}

	// Repository config
	public RepositoryConfig repository;

	@ConfigurationProperties("repositories")
	public static class RepositoryConfig {

		@Min(60)
		public int mediaTracksMaxIdleTime = 300;

		@Min(60)
		public int peerConnectionsMaxIdleTime = 300;

		@Min(60)
		public int clientMaxIdleTime = 300;

		@Min(60)
		public int sfuMaxIdleTime = 600;

		@Min(60)
		public int sfuTransportMaxIdleTime = 600;

		@Min(60)
		public int sfuRtpPadMaxIdleTime = 600;

	}

	// Evaluators Config
	public EvaluatorsConfig evaluators;

	@ConfigurationProperties("evaluators")
	public static class EvaluatorsConfig {

		@Min(1)
		public int reportSfuRtpPadWithCallIdTimeoutInS = 60;

	}

	// internal collectors config
	public InternalBuffersConfig buffers;

	@ConfigurationProperties(("buffers"))
	public static class InternalBuffersConfig {

		public ClientSamplesCollectorConfig clientSamplesCollector = new ClientSamplesCollectorConfig();

		@ConfigurationProperties("clientSamples")
		public static class ClientSamplesCollectorConfig extends CollectorConfig {

		}

		public ClientProcessDebouncersCollectorConfig clientProcessDebouncers = new ClientProcessDebouncersCollectorConfig();

		@ConfigurationProperties("clientProcessDebouncers")
		public static class ClientProcessDebouncersCollectorConfig extends CollectorConfig {

		}

		public SfuSamplesCollectorConfig sfuSamplesCollector = new SfuSamplesCollectorConfig();

		@ConfigurationProperties("clientSamples")
		public static class SfuSamplesCollectorConfig extends CollectorConfig {

		}

		public SfuProcessDebouncersCollectorConfig sfuProcessDebouncers = new SfuProcessDebouncersCollectorConfig();

		@ConfigurationProperties("sfuProcessDebouncers")
		public static class SfuProcessDebouncersCollectorConfig extends CollectorConfig {

		}

		public OutboundReportsCollectorConfig outboundReportsCollector = new OutboundReportsCollectorConfig();

		@ConfigurationProperties("outboundReports")
		public static class OutboundReportsCollectorConfig extends CollectorConfig {

		}

		public RepositoryEventsDebouncersConfig repositoryEventsDebouncers = new RepositoryEventsDebouncersConfig();

		@ConfigurationProperties("repositoryEventsDebouncers")
		public static class RepositoryEventsDebouncersConfig extends CollectorConfig {

		}
	}


	public static class CollectorConfig {

		@Min(1)
		public int maxItems = 2000;

		@Min(0)
		public int maxTimeInS = 0;

	}

	public Map<String, Object> sinks;

	// Outbound Reports Config
	public ReportsConfig reports;

	@ConfigurationProperties("reports")
	public static class ReportsConfig {
		public ReportFormat format = ReportFormat.JSON;
		public boolean sendObserverEvents = true;
		public boolean sendCallEvents = true;
		public boolean sendCallMeta = true;
		public boolean sendClientExtensions = true;
		public boolean sendInboundAudioTracks = true;
		public boolean sendInboundVideoTracks = true;
		public boolean sendOutboundAudioTracks = true;
		public boolean sendOutboundVideoTracks = true;
		public boolean sendClientTransports = true;
		public boolean sendClientDataChannels = true;
		public boolean sendMediaTracks = false;

		public boolean sendSfuEvents = true;
		public boolean sendSfuMeta = true;
		public boolean sendSfuTransports = true;
		public boolean sendSfuSctpStreams = true;

		public boolean sendSfuInboundRtpStreams = true;
		public boolean sendSfuOutboundRtpStreams = true;

    }

	// Hazelcast Config
	public HazelcastConfig hazelcast;

	@ConfigurationProperties("hazelcast")
	public static class HazelcastConfig {
		public String configFile = null;
//		public Map<String, Object> config;
		public List<String> memberNamesPool = new ArrayList<>();
		public List<String> logs = new LinkedList<>();
	}

}

