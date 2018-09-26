package com.max.licensing.controllers;


import com.max.licensing.config.LicenseServiceConfig;
import com.max.licensing.model.License;
import com.max.licensing.services.LicenseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicensesController {

    private static final Logger LOG = Logger.getLogger(LicensesController.class);

    private final LicenseService licenseService;

    private final LicenseServiceConfig config;

    @Autowired
    public LicensesController(LicenseServiceConfig config, LicenseService licenseService) {
        this.config = config;
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenseById(@PathVariable("organizationId") long organizationId,
                                  @PathVariable("licenseId") long licenseId) {
        License singleLicense = licenseService.getByIds(organizationId, licenseId);
        singleLicense.setDescription(config.getExampleProperty());

        return singleLicense;
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.POST)
    public String addNew(@PathVariable("organizationId") long organizationId,
                         @PathVariable("licenseId") long licenseId) {

        //TODO:
        licenseService.add(organizationId, licenseId, "", "");

        return "Created";
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public String update(@PathVariable("organizationId") long organizationId,
                         @PathVariable("licenseId") long licenseId) {

        //TODO:
        licenseService.update(organizationId, licenseId, "", "");

        return "Updated";
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("organizationId") long organizationId,
                         @PathVariable("licenseId") long licenseId) {

        //TODO:
        return licenseService.delete(organizationId, licenseId) ? "Deleted successfully" : "404 not found";
    }
}
