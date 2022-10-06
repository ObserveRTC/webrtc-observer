package org.observertc.observer.hamokendpoints;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.observertc.observer.configbuilders.AbstractBuilder;
import org.observertc.observer.configbuilders.Builder;
import org.observertc.observer.configbuilders.ConfigConverter;
import org.observertc.observer.hamokdiscovery.DiscoveryBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class HamokEndpointBuilderService extends AbstractBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HamokEndpointBuilderService.class);

    private final List<String> packages;
    private EndpointsBuildersEssentials essentials;

    @Inject
    DiscoveryBuilderService discoveryBuilderService;

    @PostConstruct
    void setup() {
        this.essentials = new EndpointsBuildersEssentials(
                this.discoveryBuilderService
        );
    }

    public HamokEndpointBuilderService() {
        Package thisPackage = this.getClass().getPackage();
        Package[] packages = Package.getPackages();
        this.packages = Arrays.stream(packages)
                .filter(p -> p.getName().startsWith(thisPackage.getName()))
                .map(Package::getName)
                .collect(Collectors.toList());
    }

    private UUID localEndpointId = null;
    public HamokEndpointBuilderService setEndpointId(UUID localEndpointId) {
        this.localEndpointId = localEndpointId;
        return this;
    }

    public HamokEndpoint build(Map<String, Object> configMap) {
        if (configMap == null) {
            return null;
        }
        var config = ConfigConverter.convert(EndpointBuilderConfig.class, configMap);
        String builderClassName = AbstractBuilder.getBuilderClassName("", config.type, "Builder");
        Optional<Builder> builderHolder = this.tryInvoke(builderClassName);
        if (!builderHolder.isPresent()) {
            logger.warn("Cannot find endpoint builder for {} in packages: {}", config.type, String.join(",", this.packages));
            return null;
        }
        EndpointBuilder endpointBuilder = (EndpointBuilder) builderHolder.get();
        endpointBuilder.setBuildingEssentials(this.essentials);
        endpointBuilder.withConfiguration(config.config);

        var result = endpointBuilder.build();
        return result;
    }
}
