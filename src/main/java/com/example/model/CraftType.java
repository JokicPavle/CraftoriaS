package com.example.model;

public class CraftType {

    private int id;
    private String craftName;

    public CraftType(){}

    public CraftType(int id, String craftName) {
        this.id = id;
        this.craftName = craftName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
    }
}
