package com.example.havefun.models;

public class Order {
    String id, order_type,order_status;
    Hotel hotel;
    Room room;
    User user;
    float overnight_price,daily_price,hour_price_bonus,hour_price,total_price_estimate=0,total_price_real=0;
    Timestamp order_start,order_end,created_at,payment_time;

    public float getTotal_price_real() {
        return total_price_real;
    }

    public void setTotal_price_real(float total_price_real) {
        this.total_price_real = total_price_real;
    }

    public float getTotal_price_estimate() {
        return total_price_estimate;
    }

    public void setTotal_price_estimate(float total_price_estimate) {
        this.total_price_estimate = total_price_estimate;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getOvernight_price() {
        return overnight_price;
    }

    public void setOvernight_price(float overnight_price) {
        this.overnight_price = overnight_price;
    }

    public float getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(float daily_price) {
        this.daily_price = daily_price;
    }

    public float getHour_price_bonus() {
        return hour_price_bonus;
    }

    public void setHour_price_bonus(float hour_price_bonus) {
        this.hour_price_bonus = hour_price_bonus;
    }

    public float getHour_price() {
        return hour_price;
    }

    public void setHour_price(float hour_price) {
        this.hour_price = hour_price;
    }

    public Timestamp getOrder_start() {
        return order_start;
    }

    public void setOrder_start(Timestamp order_start) {
        this.order_start = order_start;
    }

    public Timestamp getOrder_end() {
        return order_end;
    }

    public void setOrder_end(Timestamp order_end) {
        this.order_end = order_end;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Timestamp payment_time) {
        this.payment_time = payment_time;
    }
}
