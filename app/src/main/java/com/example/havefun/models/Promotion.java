package com.example.havefun.models;

import java.time.LocalDateTime;

public class Promotion {
    String [] order_type;
    LocalDateTime time_start,time_end,created_at;
    String name,description,hotel_id,id,img;
    float discount_ratio;

    public Promotion() {
    }

    public String[] getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String[] order_type) {
        this.order_type = order_type;
    }

    public LocalDateTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalDateTime time_start) {
        this.time_start = time_start;
    }

    public LocalDateTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalDateTime time_end) {
        this.time_end = time_end;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getDiscount_ratio() {
        return discount_ratio;
    }

    public void setDiscount_ratio(float discount_ratio) {
        this.discount_ratio = discount_ratio;
    }
}
