package com.tourister.models;

public class UserModel {
    private String uid;
    private String name;
    private String email;
    private int bookingCount; // optional, default 0

    public UserModel() {
        // Required empty constructor for Firestore
    }

    public UserModel(String uid, String name, String email, int bookingCount) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.bookingCount = bookingCount;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }
}
