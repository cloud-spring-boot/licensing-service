package com.max.licensing.services;

import com.max.licensing.controllers.LicensesController;
import com.max.licensing.model.License;
import com.max.licensing.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.log4j.Logger;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.Random;

@DefaultProperties(threadPoolKey = "licenseServiceThreadPool",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "30"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
        }
//        commandProperties = {
//                @HystrixProperty(
//                        name = "execution.isolation.strategy", value = "SEMAPHORE")}
)
@Service
public class LicenseService {

    private static final Random RAND = new Random();

    private static final Logger LOG = Logger.getLogger(LicensesController.class);

    private final LicenseRepository licenseRepository;

    private final Tracer tracer;

    public LicenseService(LicenseRepository licenseRepository, Tracer tracer) {
        this.licenseRepository = licenseRepository;
        this.tracer = tracer;
    }

    /**
     * coreSize = (requests per second at peak when the service is healthy * 99th percentile latency in seconds) +
     * small amount of extra threads for overhead
     *
     * @param licenseId
     * @return
     */
    @HystrixCommand(fallbackMethod = "getByIdsFallback")
    public License getByIds(String licenseId) {

        LOG.info("getByIds called");

        Span readFromDbSpan = tracer.createSpan("get_license_from_db");

        try {
            return licenseRepository.findOne(licenseId);
        }
        finally {
            readFromDbSpan.tag("peer.service", "postgresql");
            readFromDbSpan.logEvent(Span.CLIENT_RECV);
            tracer.close(readFromDbSpan);
        }
    }

    @SuppressWarnings("unused")
    private License getByIdsFallback(String licenseId) {
        License license = new License();
        license.setId(licenseId);
        license.setOrganizationId("-1");
        license.setProductName("Undefined");
        license.setLicenseType("Unknown");
        license.setLicenseMax(-1);
        license.setLicenseAllocated(-1);
        license.setComment("No comment");

        return license;
    }

    @HystrixCommand
    public boolean delete(String licenseId) {
        if (licenseRepository.exists(licenseId)) {
            licenseRepository.delete(licenseId);
            return true;
        }

        return false;
    }

    @HystrixCommand
    public void update(String licenseId, String productName, String licenseType) {
        //TODO:
    }

    @HystrixCommand
    public License add(License newLicense) {
        return licenseRepository.save(newLicense);
    }

}
