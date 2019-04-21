package com.kamilkorzeniewski.stockcontrol.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamilkorzeniewski.stockcontrol.security.user.User;

public class AuthenticationDTO {
    @JsonProperty(value = "username")
    private String userName;
    @JsonProperty(value = "password")
    private String password;

    private AuthenticationDTO() {
    }

    public AuthenticationDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    User toUser() {
        return new User(userName, password);
    }
}
