package com.max.licensing.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HelloDiscoveryClient {

    private static final Logger LOG = Logger.getLogger(HelloDiscoveryClient.class);

    private final DiscoveryClient discoveryClient;

    @Autowired
    public HelloDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public MessageDto getHelloMessage(String firstName, String lastName) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("hello-service");

        if (instances.isEmpty()) {
            return MessageDto.UNDEFINED;
        }

        LOG.info("DISCOVERED: " + instances);

        String serviceUri = String.format("%s/hello/%s/%s",
                instances.get(0).getUri().toString(),
                firstName, lastName);

        ResponseEntity<MessageDto> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET, null, MessageDto.class);

        return restExchange.getBody();
    }
}
