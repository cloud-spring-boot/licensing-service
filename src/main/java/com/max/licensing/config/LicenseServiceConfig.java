package com.max.licensing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LicenseServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    public String getExampleProperty() {
        return exampleProperty;
    }
}
