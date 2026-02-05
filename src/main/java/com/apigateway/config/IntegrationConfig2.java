package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class IntegrationConfig2 {

    /**
     * Creates a REST endpoint at /api2/hello that returns "Hello World"
     */
    @Bean
    public IntegrationFlow httpGetFlow2() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api2/hello")
                .requestMapping(m -> m.methods(HttpMethod.GET))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> "Hello World from API2")
            .get();
    }
    
    /**
     * POST endpoint that forwards the request to https://httpbin.org/post
     * Endpoint: /api2/echo
     * This endpoint receives a POST request and forwards it to the upstream service
     */
    @Bean
    public IntegrationFlow httpPostFlow2() {
        return IntegrationFlow
            .from(Http.inboundGateway("/api2/echo")
                .requestMapping(m -> m.methods(HttpMethod.POST))
                .requestPayloadType(String.class))
            .handle(Http.outboundGateway("https://httpbin.org/post")
                .httpMethod(HttpMethod.POST)
                .timeout(5000)
                .connectTimeout(5000)
                .readTimeout(5000)
                .writeTimeout(5000)
                .bufferSize(1024)
                .expectedResponseType(String.class))
            .get();
    }
}
