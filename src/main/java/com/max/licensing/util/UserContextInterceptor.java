package com.max.licensing.util;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserContextInterceptor implements RequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {

        if( LOG.isInfoEnabled() ) {
            LOG.info("Intercepting {} with correlation-id: {} ",
                    requestTemplate.url(),
                    UserContextHolder.getUserContext().getCorrelationId());
        }

        requestTemplate.header(UserContext.CORRELATION_ID_HEADER,
                UserContextHolder.getUserContext().getCorrelationId());


    }

}
