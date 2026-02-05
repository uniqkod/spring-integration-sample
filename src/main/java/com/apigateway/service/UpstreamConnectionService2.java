package com.apigateway.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * UpstreamConnectionService2 - A configurable service for scenario 4
 * This service is created dynamically via UpstreamConnectionFactory
 */
public class UpstreamConnectionService2 {

    private RestTemplate restTemplate;
    private String upstreamUrl = "https://httpbin.org/post";
    private int connectionTimeout = 5000;
    private int readTimeout = 5000;

    public UpstreamConnectionService2() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Sends a POST request to the upstream service
     * 
     * @param payload The request payload to send
     * @return The response from the upstream service
     */
    public String sendPostRequest(String payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> request = new HttpEntity<>(payload, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                upstreamUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            
            return response.getBody();
        } catch (Exception e) {
            return "Error connecting to upstream service: " + e.getMessage();
        }
    }

    // Setters for factory configuration
    public void setUpstreamUrl(String upstreamUrl) {
        this.upstreamUrl = upstreamUrl;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        rebuildRestTemplate();
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        rebuildRestTemplate();
    }

    /**
     * Rebuilds the RestTemplate with updated timeout configurations
     */
    private void rebuildRestTemplate() {
        this.restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectionTimeout))
            .setReadTimeout(Duration.ofMillis(readTimeout))
            .build();
    }
}
