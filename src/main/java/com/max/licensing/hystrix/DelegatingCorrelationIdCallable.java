package com.max.licensing.hystrix;

import com.max.licensing.util.CorrelationIdHolder;

import java.util.concurrent.Callable;

public class DelegatingCorrelationIdCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private String correlationId;

    DelegatingCorrelationIdCallable(Callable<V> delegate, String correlationId) {
        this.delegate = delegate;
        this.correlationId = correlationId;
    }

    public V call() throws Exception {
        CorrelationIdHolder.setId(correlationId);

        try {
            return delegate.call();
        }
        finally {
            this.correlationId = null;
        }
    }
}

