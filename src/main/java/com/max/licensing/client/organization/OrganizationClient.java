package com.max.licensing.client.organization;

import com.max.licensing.client.OrganizationDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Primary
@FeignClient(value = "zuul", fallback = OrganizationClientFallback.class)
public interface OrganizationClient {

    /**
     * Making a call to organization-service through Zuul as a proxy.
     */
    @RequestMapping(value = "api/organization/v1/organizations/{organizationId}", method = RequestMethod.GET)
    ResponseEntity<OrganizationDto> getOrganization(@PathVariable("organizationId") String organizationId);

}

@Component
class OrganizationClientFallback implements OrganizationClient {
    @Override
    public ResponseEntity<OrganizationDto> getOrganization(String organizationId) {
        return ResponseEntity.ok(new OrganizationDto("-1", "Defaul name"));
    }
}
