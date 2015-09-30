package com.example.users;


import java.util.Optional;

public class CreateUser {
    private final UaaClient uaaClient;

    public CreateUser(UaaClient uaaClient) {
        this.uaaClient = uaaClient;
    }
    public Optional<User> run(String name, String password) {
        return uaaClient.createUser(name, password);
    }
}
