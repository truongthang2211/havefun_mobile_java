package com.example.havefun.models;

public class Hotel {
    String name, location, promotion,timePromotion;
    double price, discount, rate, num_rate;
    int image;

    public Hotel(String name, String location, String promotion, String timePromotion, double price, double discount, double rate, double num_rate, int image) {
        this.name = name;
        this.location = location;
        this.promotion = promotion;
        this.timePromotion = timePromotion;
        this.price = price;
        this.discount = discount;
        this.rate = rate;
        this.num_rate = num_rate;
        this.image = image;
    }

    public String getTimePromotion() {
        return timePromotion;
    }

    public void setTimePromotion(String timePromotion) {
        this.timePromotion = timePromotion;
    }

    public Hotel() {

    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getNum_rate() {
        return num_rate;
    }

    public void setNum_rate(double num_rate) {
        this.num_rate = num_rate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
