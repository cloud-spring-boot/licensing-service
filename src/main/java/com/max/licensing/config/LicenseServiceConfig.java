package com.max.licensing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LicenseServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }
}
