package com.example.model;

import java.util.List;

public class CraftService {
    private int id;
    private String serviceNames;
    private CraftType craftType;
    public CraftService() {}

    public CraftService(int id, String serviceNames, CraftType craftType) {
        this.id = id;
        this.serviceNames = serviceNames;
        this.craftType = craftType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(String serviceNames) {
        this.serviceNames = serviceNames;
    }

    public CraftType getCraftType() {
        return craftType;
    }

    public void setCraftType(CraftType craftType) {
        this.craftType = craftType;
    }
}
