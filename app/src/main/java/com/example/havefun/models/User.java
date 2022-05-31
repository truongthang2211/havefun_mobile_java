package com.example.havefun.models;

public class User {
    String email,id,password,phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String email, String id, String password, String phone) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.phone = phone;
    }

    public User() {
    }

}
