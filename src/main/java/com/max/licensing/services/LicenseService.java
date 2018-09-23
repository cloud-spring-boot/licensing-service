package com.max.licensing.services;

import com.max.licensing.model.License;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LicenseService {

    private final Map<LicenseKey, License> data = new HashMap<>();

    public LicenseService() {
        data.put(new LicenseKey(1, 177), new License(1, 177, "Open JDK", "GNU"));
    }

    public License getByIds(long organizationId, long licenseId) {
        return data.get(new LicenseKey(organizationId, licenseId));
    }

    public boolean delete(long organizationId, long licenseId) {
        return data.remove(new LicenseKey(organizationId, licenseId)) != null;
    }

    public void update(long organizationId, long licenseId, String productName, String licenseType) {
        License curLicense = data.get(new LicenseKey(organizationId, licenseId));
        curLicense.setProductName(productName);
        curLicense.setLicenseType(licenseType);
    }

    public License add(long organizationId, long licenseId, String productName, String licenseType) {
        return new License(organizationId, licenseId, productName, licenseType);
    }

    private static final class LicenseKey {

        final long organizationId;
        final long licenseId;

        LicenseKey(long organizationId, long licenseId) {
            this.organizationId = organizationId;
            this.licenseId = licenseId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            LicenseKey other = (LicenseKey) obj;

            return Objects.equals(organizationId, other.organizationId) &&
                    Objects.equals(licenseId, other.licenseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(organizationId, licenseId);
        }
    }

}
