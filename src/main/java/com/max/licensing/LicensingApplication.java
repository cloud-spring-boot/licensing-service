package com.max.licensing;

import com.max.licensing.cache.OrganizationCacheRepository;
import com.max.licensing.config.LicenseServiceConfig;
import com.max.licensing.events.OrgChangeEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding(Sink.class)
public class LicensingApplication {

    private static final Logger LOG = LoggerFactory.getLogger(LicensingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicensingApplication.class, args);
    }

    @Autowired
    private LicenseServiceConfig config;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(config.getRedisHost());
        connectionFactory.setPort(config.getRedisPort());
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Autowired
    private OrganizationCacheRepository organizationCacheRepository;

    @StreamListener(Sink.INPUT)
    public void orgChangeEventListener(OrgChangeEventDto orgChangeEventDto) {

        MDC.put("correlation-id", orgChangeEventDto.getCorrelationId());

        LOG.info("Event received {} for organizationId {}",
                orgChangeEventDto.getAction(), orgChangeEventDto.getOrganizationId());

        if ("DELETE".equals(orgChangeEventDto.getAction())) {
            try {
                organizationCacheRepository.delete(orgChangeEventDto.getOrganizationId());
            }
            catch (Exception ex) {
                LOG.error("Can't delete organization with id " + orgChangeEventDto.getOrganizationId() +
                        " from Redis cache", ex);
            }
        }

    }

}
