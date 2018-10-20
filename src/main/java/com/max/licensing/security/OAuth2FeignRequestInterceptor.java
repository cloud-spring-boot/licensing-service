package com.max.licensing.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//TODO: not used
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2FeignRequestInterceptor.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN = "Bearer";


    @Override
    public void apply(RequestTemplate template) {

        LOG.info("Feign request interceptor <--------------------------------------------------------------");

        LOG.info(template.request().headers().toString());

        LOG.info("Feign request interceptor <--------------------------------------------------------------");

//        if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
//            LOG.warn("The Authorization token has been already set");
//        }
//        else if (oauth2ClientContext.getAccessTokenRequest().getExistingToken() == null) {
//            LOG.warn("Can not obtain existing token for request, if it is a non secured request, ignore.");
//        }
//        else {
//            LOG.info("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN);
//            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN,
//                    oauth2ClientContext.getAccessTokenRequest().getExistingToken().toString()));
//        }
    }
}