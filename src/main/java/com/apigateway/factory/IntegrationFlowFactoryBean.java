package com.apigateway.factory;

import com.apigateway.service.UpstreamConnectionService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

/**
 * FactoryBean for creating IntegrationFlow beans dynamically
 * This allows for programmatic bean creation without @Bean annotations
 */
@Component("api4FlowFactory")
public class IntegrationFlowFactoryBean implements FactoryBean<IntegrationFlow> {

    @Autowired
    private ApplicationContext applicationContext;

    private String endpoint;
    private HttpMethod httpMethod;
    private FlowType flowType;
    private String serviceBeanName;

    public enum FlowType {
        HELLO,
        ECHO
    }

    /**
     * Creates the IntegrationFlow bean based on configured parameters
     */
    @Override
    public IntegrationFlow getObject() throws Exception {
        if (flowType == FlowType.HELLO) {
            return createHelloFlow();
        } else if (flowType == FlowType.ECHO) {
            return createEchoFlow();
        }
        throw new IllegalStateException("FlowType must be set before creating bean");
    }

    @Override
    public Class<?> getObjectType() {
        return IntegrationFlow.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Creates a simple hello flow
     */
    private IntegrationFlow createHelloFlow() {
        return IntegrationFlow
            .from(Http.inboundGateway(endpoint)
                .requestMapping(m -> m.methods(httpMethod))
                .requestPayloadType(String.class))
            .<String, String>transform(payload -> "Hello World from API4")
            .get();
    }

    /**
     * Creates an echo flow that delegates to UpstreamConnectionService2
     */
    private IntegrationFlow createEchoFlow() {
        return IntegrationFlow
            .from(Http.inboundGateway(endpoint)
                      .requestMapping(m -> m.methods(httpMethod))
                      .requestPayloadType(String.class))
                      .<String, String>transform(payload -> {
                        UpstreamConnectionService service = applicationContext.getBean(serviceBeanName,UpstreamConnectionService.class);
                        return service.sendPostRequest(payload);
                      })
            .get();
    }

    // Setters for configuration
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public void setServiceBeanName(String serviceBeanName) {
        this.serviceBeanName = serviceBeanName;
    }
}
