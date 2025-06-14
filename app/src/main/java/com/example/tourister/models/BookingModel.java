package com.example.tourister.models;

public class BookingModel {
    private String id;
    private String userId;
    private String userName;
    private String packageId;
    private String packageTitle;
    private String packagePrice;
    private String status;

    public BookingModel() {}

    public BookingModel(String id, String userId, String userName, String packageId,
                        String packageTitle, String packagePrice, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.packageId = packageId;
        this.packageTitle = packageTitle;
        this.packagePrice = packagePrice;
        this.status = status;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }
    public String getPackageTitle() { return packageTitle; }
    public void setPackageTitle(String packageTitle) { this.packageTitle = packageTitle; }
    public String getPackagePrice() { return packagePrice; }
    public void setPackagePrice(String packagePrice) { this.packagePrice = packagePrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}