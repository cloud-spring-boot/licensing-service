package com.max.licensing.util;

public final class CorrelationIdHolder {

    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    private CorrelationIdHolder() {
    }

    public static void setId(String correlationId) {
        CORRELATION_ID.set(correlationId);
    }

    public static String getId() {
        return CORRELATION_ID.get();
    }

}
