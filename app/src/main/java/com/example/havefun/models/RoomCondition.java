package com.example.havefun.models;

public class RoomCondition {
    boolean area_20m2,double_bed,window;

    public RoomCondition() {
    }

    public RoomCondition(boolean area_20m2, boolean double_bed, boolean window) {
        this.area_20m2 = area_20m2;
        this.double_bed = double_bed;
        this.window = window;
    }

    public boolean isArea_20m2() {
        return area_20m2;
    }

    public void setArea_20m2(boolean area_20m2) {
        this.area_20m2 = area_20m2;
    }

    public boolean isDouble_bed() {
        return double_bed;
    }

    public void setDouble_bed(boolean double_bed) {
        this.double_bed = double_bed;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }
}
