package com.example.havefun.models;

import java.time.LocalDateTime;

public class Rating {
    String comment;
    float start;
    User user;
    LocalDateTime created_at;

    public Rating() {
    }

    public Rating(String comment, float start, User user, LocalDateTime created_at) {
        this.comment = comment;
        this.start = start;
        this.user = user;
        this.created_at = created_at;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
