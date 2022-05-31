package com.example.havefun.models;

public class Facility {
    boolean wifi,wood_floor,tv, reception24,elevator,cable_tv,air_conditioning;

    public Facility(boolean wifi, boolean wood_floor, boolean tv, boolean reception24, boolean elevator, boolean cable_tv, boolean air_conditioning) {
        this.wifi = wifi;
        this.wood_floor = wood_floor;
        this.tv = tv;
        this.reception24 = reception24;
        this.elevator = elevator;
        this.cable_tv = cable_tv;
        this.air_conditioning = air_conditioning;
    }

    public Facility() {
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isWood_floor() {
        return wood_floor;
    }

    public void setWood_floor(boolean wood_floor) {
        this.wood_floor = wood_floor;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isReception24() {
        return reception24;
    }

    public void setReception24(boolean reception24) {
        this.reception24 = reception24;
    }

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public boolean isCable_tv() {
        return cable_tv;
    }

    public void setCable_tv(boolean cable_tv) {
        this.cable_tv = cable_tv;
    }

    public boolean isAir_conditioning() {
        return air_conditioning;
    }

    public void setAir_conditioning(boolean air_conditioning) {
        this.air_conditioning = air_conditioning;
    }
}
