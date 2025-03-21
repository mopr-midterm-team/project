package com.example.repo_midterm.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Phone implements Serializable {

    @SerializedName("phoneID")
    private int phoneID;

    @SerializedName("phoneName")
    private String phoneName;

    @SerializedName("price")
    private double price;

    @SerializedName("color")
    private String color;

    @SerializedName("status")
    private boolean status;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    // Constructor
    public Phone(int phoneID, String phoneName, double price, String color, boolean status, String description, String image) {
        this.phoneID = phoneID;
        this.phoneName = phoneName;
        this.price = price;
        this.color = color;
        this.status = status;
        this.description = description;
        this.image = image;
    }

    // Getters và Setters
    public int getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(int phoneID) {
        this.phoneID = phoneID;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imagePhone) {
        this.image = imagePhone;
    }


}
