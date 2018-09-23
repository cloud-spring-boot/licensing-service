package com.max.licensing.model;

public class License {

    private final long id;
    private final long organizationId;
    private String productName;
    private String licenseType;


    public License(long id, long organizationId, String productName, String licenseType) {
        this.id = id;
        this.organizationId = organizationId;
        this.productName = productName;
        this.licenseType = licenseType;
    }

    public long getId() {
        return id;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
}
