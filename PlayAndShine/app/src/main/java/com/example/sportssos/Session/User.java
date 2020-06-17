package com.example.sportssos.Session;

import java.net.UnknownServiceException;

public class User {

    public String email, name, phone, location, type;

    public User() {
        email = "";
        name = "";
        phone = "";
        location = "";
        type = "";
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }
}
