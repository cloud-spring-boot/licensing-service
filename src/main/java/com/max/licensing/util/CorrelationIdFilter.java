package com.max.licensing.util;

import org.apache.log4j.Logger;
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
public class CorrelationIdFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CorrelationIdFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;

        String correlationIdValue = httpReq.getHeader("Correlation-Id");

        LOG.info("correlationId: " + correlationIdValue);

        CorrelationIdHolder.setId(correlationIdValue);

        chain.doFilter(req, resp);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
