package com.max.licensing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LicenseServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }
}
