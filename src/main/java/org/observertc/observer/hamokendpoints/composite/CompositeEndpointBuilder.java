package org.observertc.observer.hamokendpoints.composite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.balazskreith.hamok.storagegrid.messages.Message;
import io.micronaut.context.annotation.Prototype;
import org.observertc.observer.configbuilders.AbstractBuilder;
import org.observertc.observer.configs.InvalidConfigurationException;
import org.observertc.observer.hamokendpoints.BuildersEssentials;
import org.observertc.observer.hamokendpoints.EndpointBuilder;
import org.observertc.observer.hamokendpoints.HamokEndpoint;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.function.Function;

@Prototype
public class CompositeEndpointBuilder extends AbstractBuilder implements EndpointBuilder {

    private UUID endpointId;
    private BuildersEssentials essentials;

    @Override
    public HamokEndpoint build() {
        var config = this.convertAndValidate(CompositeEndpointConfig.class);
        var mapper = new ObjectMapper();
        Function<Message, byte[]> encoder = message -> {
            try {
                return mapper.writeValueAsBytes(message);
            } catch (JsonProcessingException e) {
                logger.error("Error occurred while serializing message {}", message, e);
                return null;
            }
        };
        Function<byte[], Message> decoder = bytes -> {
            try {
                return mapper.readValue(bytes, Message.class);
            } catch (IOException e) {
                logger.error("Error occurred while serializing message", e);
                return null;
            }
        };
        InetAddress multicastAddress;
        try {
            multicastAddress = InetAddress.getByName(config.multicastAddress);
        } catch (UnknownHostException e) {
            logger.error("Cannot convert {} to a multicastAddress", config.multicastAddress, e);
            return null;
        }
        if (!multicastAddress.isMulticastAddress()) {
            throw new InvalidConfigurationException("The provided address " + config.multicastAddress + " is not a multicast address");
        }
        return new TcpEndpoint(config);
//        var endpoint = CompositeEndpoint.builder()
//                .setUnicastListenerPort(config.unicastListeningPort)
//                .setUnicastSendingPort(config.unicastSendingPort)
//                .setMulticastPort(config.multicastPort)
//                .setEndpointId(this.endpointId)
//                .setContext(config.context)
//                .setMulticastAddress(multicastAddress)
//                .setEncoder(encoder)
//                .setDecoder(decoder)
//                .build();
//        var createdInSec = Instant.now().getEpochSecond();
//        return new HamokEndpoint() {
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//
//            @Override
//            public int elapsedSecSinceReady() {
//                return (int) (Instant.now().getEpochSecond() - createdInSec);
//            }
//
//            @Override
//            public Observable<Message> inboundChannel() {
//                return endpoint.inboundChannel();
//            }
//
//            @Override
//            public Observer<Message> outboundChannel() {
//                return endpoint.outboundChannel();
//            }
//
//            @Override
//            public void start() {
//                endpoint.start();
//            }
//
//            @Override
//            public boolean isRunning() {
//                return endpoint.isRunning();
//            }
//
//            @Override
//            public void stop() {
//                endpoint.stop();
//            }
//        };
    }

    public void setBuildingEssentials(BuildersEssentials essentials) {
        this.essentials = essentials;
    }

}
