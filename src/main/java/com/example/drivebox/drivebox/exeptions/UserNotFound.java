package com.example.drivebox.drivebox.exeptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String id) {
        super("A user with id '" + id + "' does not exist");
    }
}
