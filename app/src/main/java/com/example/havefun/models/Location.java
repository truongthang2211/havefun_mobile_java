package com.example.havefun.models;

public class Location {
    String address,village,city,district;

    public Location() {
    }

    public Location(String address, String village, String city, String district) {
        this.address = address;
        this.village = village;
        this.city = city;
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
