package com.max.licensing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class UserContextFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;

        final UserContext userContext = new UserContext(httpReq.getHeader(UserContext.CORRELATION_ID_HEADER));
        UserContextHolder.setUserContext(userContext);

        LOG.info("Request with Correlation-Id: {}", userContext.getCorrelationId());

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // not implemented
    }

    @Override
    public void destroy() {
        // not implemented
    }
}
