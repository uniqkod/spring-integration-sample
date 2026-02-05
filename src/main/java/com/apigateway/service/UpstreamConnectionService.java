package com.apigateway.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UpstreamConnectionService {

    private final RestTemplate restTemplate;
    private static final String UPSTREAM_URL = "https://httpbin.org/post";

    public UpstreamConnectionService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Sends a POST request to the upstream service (httpbin.org/post)
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
                UPSTREAM_URL,
                HttpMethod.POST,
                request,
                String.class
            );
            
            return response.getBody();
        } catch (Exception e) {
            return "Error connecting to upstream service: " + e.getMessage();
        }
    }
}
