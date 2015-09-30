package com.example.users;

import java.util.Optional;

public class LogIn {
    private final UaaClient uaaClient;

    public LogIn(UaaClient uaaClient) {
        this.uaaClient = uaaClient;
    }

    public Optional<Session> run(String name, String password) {
        return uaaClient.logIn(name, password);
    }
}
