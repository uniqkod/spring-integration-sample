package com.apigateway.factory;

import com.apigateway.service.UpstreamConnectionService2;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * FactoryBean for creating UpstreamConnectionService2 instances dynamically
 * This allows for programmatic service creation with custom configuration
 */
@Component("upstreamConnectionFactory")
public class UpstreamConnectionFactory implements FactoryBean<UpstreamConnectionService2> {

    private String upstreamUrl;
    private int connectionTimeout = 5000;
    private int readTimeout = 5000;

    /**
     * Creates the UpstreamConnectionService2 bean
     */
    @Override
    public UpstreamConnectionService2 getObject() throws Exception {
        UpstreamConnectionService2 service = new UpstreamConnectionService2();
        
        // Configure the service with factory parameters
        if (upstreamUrl != null && !upstreamUrl.isEmpty()) {
            service.setUpstreamUrl(upstreamUrl);
        }
        
        service.setConnectionTimeout(connectionTimeout);
        service.setReadTimeout(readTimeout);
        
        return service;
    }

    @Override
    public Class<?> getObjectType() {
        return UpstreamConnectionService2.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    // Setters for configuration
    public void setUpstreamUrl(String upstreamUrl) {
        this.upstreamUrl = upstreamUrl;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}

