package com.example.model;

import java.util.List;

public class Craftman {
    private int id;
    private String description;
    private int experience;
    private User user;
    private List<String> images;
    private List<CraftService> services;

    public Craftman() {}

    public Craftman(int id, String description, int experience, User user, List<String> images, List<CraftService> services) {
        this.id = id;
        this.description = description;
        this.experience = experience;
        this.user = user;
        this.images = images;
        this.services = services;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
