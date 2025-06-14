package com.example.tourister.models;

import java.io.Serializable;

public class PackageModel implements Serializable {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String price;
    private String season;
    private boolean available;

    // Firestore requires empty constructor
    public PackageModel() {}

    public PackageModel(String id, String title, String description,
                        String imageUrl, String price, String season, boolean available) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.season = season;
        this.available = available;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}