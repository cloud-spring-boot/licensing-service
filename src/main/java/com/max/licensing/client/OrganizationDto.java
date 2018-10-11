package com.max.licensing.client;

public class OrganizationDto {

    private final String id;

    private final String name;

    public OrganizationDto() {
        this(null, null);
    }

    public OrganizationDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
