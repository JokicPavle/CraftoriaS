package com.example.model;

import java.util.List;

public class Craftman {
    private int id;
    private String description;
    private int experience;
    private int userId;
    private List<String> images;
    private List<CraftService> services;

    public Craftman() {}

    public Craftman(int id, String description, int experience, int userId, List<String> images, List<CraftService> services) {
        this.id = id;
        this.description = description;
        this.experience = experience;
        this.userId = userId;
        this.images = images;
        this.services = services;
    }
    public Craftman(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<CraftService> getServices() {
        return services;
    }

    public void setServices(List<CraftService> services) {
        this.services = services;
    }
}
