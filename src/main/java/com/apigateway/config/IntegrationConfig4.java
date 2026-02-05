package com.apigateway.config;

import com.apigateway.factory.IntegrationFlowFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Configuration class that dynamically registers IntegrationFlow beans
 * using FactoryBean pattern instead of @Bean annotations
 */
@Configuration
public class IntegrationConfig4 implements BeanDefinitionRegistryPostProcessor {

    /**
     * Dynamically registers bean definitions for /api4 endpoints
     * This method is called during Spring container initialization
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Register the hello flow for GET /api4/hello
        registerHelloFlow(registry);
        
        // Register the echo flow for POST /api4/echo
        registerEchoFlow(registry);
    }

    /**
     * Registers a bean definition for the hello endpoint
     */
    private void registerHelloFlow(BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder
            .genericBeanDefinition(IntegrationFlowFactoryBean.class)
            .addPropertyValue("endpoint", "/api4/hello")
            .addPropertyValue("httpMethod", HttpMethod.GET)
            .addPropertyValue("flowType", IntegrationFlowFactoryBean.FlowType.HELLO)
            .getBeanDefinition();
        
        registry.registerBeanDefinition("httpGetFlow4", beanDefinition);
    }

    /**
     * Registers a bean definition for the echo endpoint
     */
    private void registerEchoFlow(BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder
            .genericBeanDefinition(IntegrationFlowFactoryBean.class)
            .addPropertyValue("endpoint", "/api4/echo")
            .addPropertyValue("httpMethod", HttpMethod.POST)
            .addPropertyValue("flowType", IntegrationFlowFactoryBean.FlowType.ECHO)
            .addPropertyValue("serviceBeanName", "upstreamConnectionService4")
            .getBeanDefinition();
        
        registry.registerBeanDefinition("httpPostFlow4", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // No post-processing needed for bean factory
    }
}
