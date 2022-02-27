package com.prumi.web.api.core;

public class AdressOrderDto {
    private String countryCode;
    private String postalCode;
    private String adminArea1;
    private String adminArea2;
    private String addressLine1;
    private String addressLine2;

    public AdressOrderDto() {
    }

    public AdressOrderDto(String countryCode, String postalCode, String adminArea1, String adminArea2, String addressLine1, String addressLine2) {
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.adminArea1 = adminArea1;
        this.adminArea2 = adminArea2;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdminArea1() {
        return adminArea1;
    }

    public void setAdminArea1(String adminArea1) {
        this.adminArea1 = adminArea1;
    }

    public String getAdminArea2() {
        return adminArea2;
    }

    public void setAdminArea2(String adminArea2) {
        this.adminArea2 = adminArea2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
}
