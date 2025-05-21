package com.example.dto;

import com.example.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

        private int id;
        private String fullName;
        private String username;
        private String email;
        private String password;
        private String city;
        private String phone;
        private boolean isCraftman;
        private String description;
        private int experience;
        private double avgRating;

        private List<Comment> comments = new ArrayList<>();
        private List<ServiceDTO> services = new ArrayList<>();
        private List<String> images = new ArrayList<>();



    public UserDTO() {}

//Contructor without PASSWORD FIELD
    public UserDTO(int id, String fullName, String username, String email, String city, String phone,
                   boolean isCraftman, String description, int experience, double avgRating,
                   List<Comment> comments, List<ServiceDTO> services, List<String> images) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.isCraftman = isCraftman;
        this.description = description;
        this.experience = experience;
        this.avgRating = avgRating;
        this.comments = comments;
        this.services = services;
        this.images = images;
    }
//Constructor with PASSWORD FIELD
    public UserDTO(int id, String fullName, String username, String email, String password,
                   String city, String phone, boolean isCraftman, String description, int experience,
                   double avgRating, List<Comment> comments, List<ServiceDTO> services, List<String> images) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.isCraftman = isCraftman;
        this.description = description;
        this.experience = experience;
        this.avgRating = avgRating;
        this.comments = comments;
        this.services = services;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCraftman() {
        return isCraftman;
    }

    public void setCraftman(boolean craftman) {
        isCraftman = craftman;
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

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
