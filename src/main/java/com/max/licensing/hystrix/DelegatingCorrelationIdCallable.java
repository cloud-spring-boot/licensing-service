package com.max.licensing.hystrix;

import com.max.licensing.util.UserContext;
import com.max.licensing.util.UserContextHolder;

import java.util.concurrent.Callable;

public class DelegatingCorrelationIdCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private UserContext userContext;

    DelegatingCorrelationIdCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.userContext = userContext;
    }

    public V call() throws Exception {
        UserContextHolder.setUserContext(userContext);

        try {
            return delegate.call();
        }
        finally {
            this.userContext = null;
            UserContextHolder.clear();
        }
    }
}

