package com.max.licensing.client.organization;


import com.max.licensing.client.OrganizationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDataRetriever {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationDataRetriever.class);

    private final OrganizationClient organizationClient;
    private final OrganizationCacheRepository organizationCacheRepository;

    @Autowired
    public OrganizationDataRetriever(OrganizationClient organizationClient,
                                     OrganizationCacheRepository organizationCacheRepository) {
        this.organizationClient = organizationClient;
        this.organizationCacheRepository = organizationCacheRepository;
    }


    public OrganizationDto getOrganization(String organizationId) {

        OrganizationCached orgFromCache =
                organizationCacheRepository.findOne(organizationId);

        if (orgFromCache != null) {

            LOG.info("Found in Redis cache {}", orgFromCache);

            return new OrganizationDto(orgFromCache.getId(), orgFromCache.getName());
        }

        OrganizationDto orgFromRemoteCall =
                organizationClient.getOrganization(organizationId).getBody();

        LOG.info("Get from remote call {}", orgFromRemoteCall);

        organizationCacheRepository.save(new OrganizationCached(orgFromRemoteCall.getId(), orgFromRemoteCall.getName()));

        LOG.info("Cached in redis");

        return orgFromRemoteCall;
    }
}
