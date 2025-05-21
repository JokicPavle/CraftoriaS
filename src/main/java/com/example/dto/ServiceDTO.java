package com.example.dto;

import java.util.Objects;

public class ServiceDTO {

    private int id;               // jedinstveni ID usluge
    private String serviceName;
    private int craftTypeId;
    private String craftTypeName;

    public ServiceDTO() {}

    public ServiceDTO(int id, String serviceName, int craftTypeId, String craftTypeName) {
        this.id = id;
        this.serviceName = serviceName;
        this.craftTypeId = craftTypeId;
        this.craftTypeName = craftTypeName;
    }

    // Getteri i setteri

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getCraftTypeId() {
        return craftTypeId;
    }

    public void setCraftTypeId(int craftTypeId) {
        this.craftTypeId = craftTypeId;
    }

    public String getCraftTypeName() {
        return craftTypeName;
    }

    public void setCraftTypeName(String craftTypeName) {
        this.craftTypeName = craftTypeName;
    }

    // equals i hashCode po id i serviceName (jedinstvenost)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDTO)) return false;
        ServiceDTO that = (ServiceDTO) o;
        return id == that.id &&
                Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName);
    }
}
