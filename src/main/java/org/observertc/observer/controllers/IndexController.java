package org.observertc.observer.controllers;///*

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import org.observertc.observer.ObserverService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
public class IndexController {

	@Inject
	ObserverService observerService;

	@PostConstruct
	void setup() {
	}

	@PreDestroy
	void teardown() {
	}

	public IndexController() {

	}

	@Get()
	public HttpStatus index() {
//		if (!this.observerService.isReady()) {
//			return HttpStatus.SERVICE_UNAVAILABLE;
//		}
		return HttpStatus.OK;
	}



	@Secured(SecurityRule.IS_ANONYMOUS)
	@Get("/about")
	public Map<String, Object> about() {
		return Map.of(
//				"version", Runtime.Version.parse("0.7.0");
		);
	}

}
