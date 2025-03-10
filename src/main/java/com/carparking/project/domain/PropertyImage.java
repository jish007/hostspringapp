package com.carparking.project.domain;

public class PropertyImage {

    private String image;
    private String propertyLocation;
    private String propertyName;
    private String image2;
    private String propertyDesc;
    private String propertyOwner;
    private String ratePerHour;
    private String adminMailId;

    // Default constructor
    public PropertyImage() {}

    public PropertyImage(String image, String propertyLocation, String propertyName, String image2,String propertyDesc) {
        this.image = image;
        this.propertyLocation = propertyLocation;
        this.propertyName = propertyName;
        this.image2 = image2;
        this.propertyDesc = propertyDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return "PropertyImageDto{" +
                "image='" + image + '\'' +
                ", propertyLocation='" + propertyLocation + '\'' +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }

    public String getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(String propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public String getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(String ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getAdminMailId() {
        return adminMailId;
    }

    public void setAdminMailId(String adminMailId) {
        this.adminMailId = adminMailId;
    }
}
