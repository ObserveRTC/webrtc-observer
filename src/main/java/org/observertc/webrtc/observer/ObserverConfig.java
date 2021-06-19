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

package org.observertc.webrtc.observer;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.EachProperty;
import org.observertc.webrtc.observer.configbuilders.ConfigAssent;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ConfigurationProperties("observer")
public class ObserverConfig {

	// Security Configurations
	public SecurityConfig security;

	@ConfigurationProperties("security")
	public static class SecurityConfig {

		public SecurityConfig.WebsocketSecurityConfig websockets = new SecurityConfig.WebsocketSecurityConfig();

		@ConfigurationProperties("websockets")
		public static class WebsocketSecurityConfig {
			public int expirationInMin = 0; // 0 means the access token provided is used
		}
	}

	// Sources Config
	public SourcesConfig sources;

	@ConfigurationProperties("sources")
	public static class SourcesConfig {

		@Deprecated(since = "0.9.0")
		public SourcesConfig.PCSamplesConfig pcSamples;

		@ConfigurationProperties("pcSamples")
		public static class PCSamplesConfig {
			public boolean enabled = true;
			public String defaultServiceId = "defaultServiceName";
		}

		public SourcesConfig.ClientSamplesConfig clientSamples;

		@ConfigurationProperties("clientSamples")
		public static class ClientSamplesConfig {
			public boolean enabled = true;
			public String defaultServiceId = "defaultServiceName";
		}
	}

	// Evaluators Config
	public EvaluatorsConfig evaluators;

	@ConfigurationProperties("evaluators")
	public static class EvaluatorsConfig {

		@Min(0)
		public int clientSamplesBufferMaxTimeInS = 30;

		@Min(1)
		public int clientSamplesBufferMaxItems = 10000;
	}

	public Map<String, Object> sinks;

	// Outbound Reports Config
	public OutboundReportsConfig outboundReports;

	@ConfigurationProperties("outboundReports")
	public static class OutboundReportsConfig {
		public boolean reportInboundAudioTracks = true;
	}

	// Hazelcast Config
	public HazelcastConfig hazelcast;

	@ConfigurationProperties("hazelcast")
	public static class HazelcastConfig {
		public String configFile = null;
	}

}

