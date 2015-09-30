package com.example.users;

public class Session {
    private final String jwtToken;

    public Session(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
