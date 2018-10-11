package com.max.licensing.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organization-service")
@RequestMapping(value = "v1/organizations/")
public interface OrganizationClient {

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    ResponseEntity<OrganizationDto> getOrganization(@PathVariable("organizationId") String organizationId);
}
