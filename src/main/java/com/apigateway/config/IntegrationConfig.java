package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class IntegrationConfig {

    /**
     * Creates a REST endpoint at /api/hello that returns "Hello World"
     * You can customize the URI by changing the path in the requestMapping
     */
    @Bean
    public IntegrationFlow httpGetFlow() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api/hello")
                .requestMapping(m -> m.methods(HttpMethod.GET))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> "Hello World")
            .get();
    }
    
    /**
     * Alternative example: POST endpoint that echoes back the request
     * Endpoint: /api/echo
     */
    @Bean
    public IntegrationFlow httpPostFlow() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api/echo")
                .requestMapping(m -> m.methods(HttpMethod.POST))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> "Echo: " + payload)
            .get();
    }
}
