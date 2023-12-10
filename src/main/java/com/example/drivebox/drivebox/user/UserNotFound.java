package com.example.drivebox.drivebox.user;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String id) {
        super("User: " + id + " does not exist");
    }
}
