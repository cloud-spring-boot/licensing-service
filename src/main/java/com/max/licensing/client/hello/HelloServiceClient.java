package com.max.licensing.client.hello;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("hello-service")
@RequestMapping(value = "hello")
public interface HelloServiceClient {

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    MessageDto getHelloMessage(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName);

}
