package com.max.licensing.client.organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationCacheRepository extends CrudRepository<OrganizationCached, String> {
}
