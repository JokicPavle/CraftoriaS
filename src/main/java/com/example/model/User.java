package com.example.model;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private City city;
    private String phone;
    private String profileImage;
    private boolean isCraftman;

    public User() {}

    public User(int id, String fullName, String username, String email, String password, City city, String phone, String profileImage, boolean isCraftman) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.profileImage = profileImage;
        this.isCraftman = isCraftman;
    }

    public User(String fullName, String email, String username, String password, City city, String phone,String profileImage, boolean isCraftman) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.profileImage = profileImage;
        this.isCraftman = isCraftman;
    }
    public User(String fullName, String email, String password, boolean isCraftman) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isCraftman = isCraftman;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isCraftman() {
        return isCraftman;
    }

    public void setCraftman(boolean craftman) {
        isCraftman = craftman;
    }
}


