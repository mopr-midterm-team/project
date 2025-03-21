package com.example.repo_midterm.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String userID;
    private String username;
    private String password;
    private String name;
    private String gender;
    private Date dob;

    public User() {}

    public User(String userID, String username, String password, String name, String gender, Date dob) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                '}';
    }
}
