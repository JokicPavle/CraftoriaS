package com.example.dto;

import java.util.List;

public class UserWithServicesDTO {

    private UserDTO user;
    private List<ServiceDTO> services;

    public UserWithServicesDTO(){};

    public UserWithServicesDTO(UserDTO user, List<ServiceDTO> services) {
        this.user = user;
        this.services = services;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
}
