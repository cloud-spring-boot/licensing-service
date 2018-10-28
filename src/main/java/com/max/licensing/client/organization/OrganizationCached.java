package com.max.licensing.client.organization;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("organization_cache")
public class OrganizationCached implements java.io.Serializable {

    private String id;

    private String name;

    public OrganizationCached(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrganizationCached() {
        this(null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ", " + name;
    }
}
