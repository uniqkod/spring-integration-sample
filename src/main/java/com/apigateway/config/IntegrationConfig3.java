package com.apigateway.config;

import com.apigateway.service.UpstreamConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class IntegrationConfig3 {

    @Autowired
    private UpstreamConnectionService upstreamConnectionService;

    /**
     * Creates a REST endpoint at /api3/hello that returns "Hello World"
     */
    @Bean
    public IntegrationFlow httpGetFlow3() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api3/hello")
                .requestMapping(m -> m.methods(HttpMethod.GET))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> "Hello World from API3")
            .get();
    }
    
    /**
     * POST endpoint that forwards the request to upstream service via UpstreamConnectionService
     * Endpoint: /api3/echo
     * This endpoint receives a POST request and delegates to the service layer
     */
    @Bean
    public IntegrationFlow httpPostFlow3() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api3/echo")
                .requestMapping(m -> m.methods(HttpMethod.POST))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> upstreamConnectionService.sendPostRequest(payload))
            .get();
    }
}
