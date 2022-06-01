package com.example.havefun.models;

public class Room {
    String id,room_id,description,name,room_type;
    Facility facilities;
    float hour_price_bonus,overnight_price,daily_price,hour_price;
    String [] imgs;
    RoomCondition room_conditions;
    Timestamp created_at;

    public Room(String id, String room_id, String description, String name, String room_type, Facility facilities, float hour_price_bonus, float overnight_price, float daily_price, float hour_price, String[] imgs, RoomCondition room_conditions, Timestamp created_at) {
        this.id = id;
        this.room_id = room_id;
        this.description = description;
        this.name = name;
        this.room_type = room_type;
        this.facilities = facilities;
        this.hour_price_bonus = hour_price_bonus;
        this.overnight_price = overnight_price;
        this.daily_price = daily_price;
        this.hour_price = hour_price;
        this.imgs = imgs;
        this.room_conditions = room_conditions;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public Facility getFacilities() {
        return facilities;
    }

    public void setFacilities(Facility facilities) {
        this.facilities = facilities;
    }

    public float getHour_price_bonus() {
        return hour_price_bonus;
    }

    public void setHour_price_bonus(float hour_price_bonus) {
        this.hour_price_bonus = hour_price_bonus;
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

    public float getHour_price() {
        return hour_price;
    }

    public void setHour_price(float hour_price) {
        this.hour_price = hour_price;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public RoomCondition getRoom_conditions() {
        return room_conditions;
    }

    public void setRoom_conditions(RoomCondition room_conditions) {
        this.room_conditions = room_conditions;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Room() {
    }
}
