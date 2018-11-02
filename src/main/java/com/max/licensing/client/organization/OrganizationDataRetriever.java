package com.max.licensing.client.organization;


import com.max.licensing.cache.OrganizationCacheRepository;
import com.max.licensing.cache.OrganizationCached;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDataRetriever {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationDataRetriever.class);

    private final OrganizationClient organizationClient;
    private final OrganizationCacheRepository organizationCacheRepository;
    private final Tracer tracer;

    @Autowired
    public OrganizationDataRetriever(OrganizationClient organizationClient,
                                     OrganizationCacheRepository organizationCacheRepository,
                                     Tracer tracer) {
        this.organizationClient = organizationClient;
        this.organizationCacheRepository = organizationCacheRepository;
        this.tracer = tracer;
    }


    public OrganizationDto getOrganization(String organizationId) {

        OrganizationCached orgFromCache = getFromCache(organizationId);

        if (orgFromCache != null) {

            LOG.info("Found in Redis cache {}", orgFromCache);
            return new OrganizationDto(orgFromCache.getId(), orgFromCache.getName());
        }

        OrganizationDto orgFromRemoteCall =
                organizationClient.getOrganization(organizationId).getBody();

        LOG.info("Get from remote call {}", orgFromRemoteCall);

        storeInCache(orgFromRemoteCall);

        LOG.info("Cached in redis");

        return orgFromRemoteCall;
    }

    private OrganizationCached getFromCache(String organizationId) {

        Span readFromRedisSpan = tracer.createSpan("get_organization_from_redis");

        try {
            return organizationCacheRepository.findOne(organizationId);
        }
        catch (Exception ex) {
            // handle all Redis exceptions if any
            LOG.error("Can't obtain organization data from Redis cache", ex);
        }
        finally {
            readFromRedisSpan.tag("peer.service", "redis");
            readFromRedisSpan.logEvent(Span.CLIENT_RECV);
            tracer.close(readFromRedisSpan);
        }
        return null;
    }

    private void storeInCache(OrganizationDto orgFromRemoteCall) {
        try {
            organizationCacheRepository.save(new OrganizationCached(orgFromRemoteCall.getId(), orgFromRemoteCall.getName()));
        }
        catch (Exception ex) {
            // handle all Redis exceptions if any
            LOG.error("Can't store data in Redis cache");
        }
    }
}
