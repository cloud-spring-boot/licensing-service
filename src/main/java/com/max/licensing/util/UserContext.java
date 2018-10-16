package com.max.licensing.util;

public final class UserContext {

    public static final String CORRELATION_ID_HEADER = "Correlation-Id";

    private final String correlationId;

    public UserContext(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

}
