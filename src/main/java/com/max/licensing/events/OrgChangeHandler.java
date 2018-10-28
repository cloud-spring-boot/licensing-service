package com.max.licensing.events;

import com.max.licensing.cache.OrganizationCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(InboundOrgChangesCustomChannel.class)
public class OrgChangeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OrgChangeHandler.class);

    private final OrganizationCacheRepository organizationCacheRepository;

    @Autowired
    public OrgChangeHandler(OrganizationCacheRepository organizationCacheRepository) {
        this.organizationCacheRepository = organizationCacheRepository;
    }

    @StreamListener("inboundOrgChanges")
    public void orgChangeEventListener(OrgChangeEventDto orgChangeEventDto) {

        MDC.put("correlation-id", orgChangeEventDto.getCorrelationId());

        LOG.info("Event received {} for organizationId {}",
                orgChangeEventDto.getAction(), orgChangeEventDto.getOrganizationId());

        if ("DELETE".equals(orgChangeEventDto.getAction())) {
            try {
                organizationCacheRepository.delete(orgChangeEventDto.getOrganizationId());
            }
            catch (Exception ex) {
                LOG.error("Can't delete organization with id " + orgChangeEventDto.getOrganizationId() +
                        " from Redis cache", ex);
            }
        }

    }
}
