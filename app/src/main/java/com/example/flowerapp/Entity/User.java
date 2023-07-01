package com.example.flowerapp.Entity;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    private String username;
    private String fullname;
    private String password;
    private String address;
    private String numberphone;
    private boolean role;
    private String created_at;
    private boolean status;

    public User(String username, String fullname, String password, String address, boolean role, boolean status, String numberphone, String created_at) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.address = address;
        this.role = role;
        this.status = status;
        this.numberphone = numberphone;
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

}
