package com.max.licensing.controllers;


import com.max.licensing.client.organization.OrganizationClient;
import com.max.licensing.config.LicenseServiceConfig;
import com.max.licensing.dto.LicenseDto;
import com.max.licensing.model.License;
import com.max.licensing.services.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@PreAuthorize("hasRole('ROLE_ADMIN')")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicensesController {

    private static final Logger LOG = LoggerFactory.getLogger(LicensesController.class);

    private final LicenseService licenseService;

    private final LicenseServiceConfig config;

    private final OrganizationClient organizationClient;

    @Autowired
    public LicensesController(LicenseServiceConfig config, LicenseService licenseService,
                              OrganizationClient organizationClient) {
        this.config = config;
        this.licenseService = licenseService;
        this.organizationClient = organizationClient;
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public ResponseEntity<License> getLicenseById(@PathVariable("organizationId") String organizationId,
                                                  @PathVariable("licenseId") String licenseId) {

        LOG.info("getLicenseById called");

        License license = licenseService.getByIds(licenseId);

        if (license == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        license.setOrganizationName(organizationClient.getOrganization(organizationId).getBody().getName());

        return ResponseEntity.status(HttpStatus.OK).body(license);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.POST)
    public ResponseEntity<String> addNew(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId,
                                         @RequestBody LicenseDto licenseDto) {

        License newLicense = new License();
        newLicense.setId(licenseId);
        newLicense.setOrganizationId(organizationId);
        newLicense.setProductName(licenseDto.getProductName());
        newLicense.setLicenseType(licenseDto.getLicenseType());
        newLicense.setLicenseAllocated(licenseDto.getLicenseAllocated());
        newLicense.setLicenseMax(licenseDto.getLicenseMax());

        newLicense.setComment("Comment: " + config.getExampleProperty());

        licenseService.add(newLicense);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId) {

        boolean wasDeleted = licenseService.delete(licenseId);

        if (wasDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
