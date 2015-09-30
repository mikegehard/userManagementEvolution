package com.example.users;

public class User {
    private final String userName;
    private final String id;

    public User(String userName, String id) {

        this.userName = userName;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }
}
