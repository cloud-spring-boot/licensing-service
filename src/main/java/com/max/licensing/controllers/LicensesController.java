package com.max.licensing.controllers;


import com.max.licensing.config.LicenseServiceConfig;
import com.max.licensing.dto.LicenseDto;
import com.max.licensing.model.License;
import com.max.licensing.services.LicenseService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicensesController {

    private final LicenseService licenseService;

    private final LicenseServiceConfig config;

    @Autowired
    public LicensesController(LicenseServiceConfig config, LicenseService licenseService) {
        this.config = config;
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public ResponseEntity<License> getLicenseById(@PathVariable("organizationId") String organizationId,
                                                  @PathVariable("licenseId") String licenseId) {

        License license = licenseService.getByIds(licenseId);

        if (license == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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

        newLicense.setComment(config.getExampleProperty() + ": " +
                makeRemoteCall("http://hello-service:6060/hello/maksym/stepanenko"));

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


    private static String makeRemoteCall(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (IOException ioEx) {
            throw new IllegalStateException(ioEx);
        }
    }
}
