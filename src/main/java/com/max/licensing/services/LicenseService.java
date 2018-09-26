package com.max.licensing.services;

import com.max.licensing.model.License;
import com.max.licensing.repository.LicenseRepository;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {


    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License getByIds(String licenseId) {
        return licenseRepository.findOne(licenseId);
    }

    public boolean delete(String licenseId) {
        if (licenseRepository.exists(licenseId)) {
            licenseRepository.delete(licenseId);
            return true;
        }

        return false;
    }

    public void update(String licenseId, String productName, String licenseType) {
        //TODO:
    }

    public License add(License newLicense) {
        return licenseRepository.save(newLicense);
    }


}
