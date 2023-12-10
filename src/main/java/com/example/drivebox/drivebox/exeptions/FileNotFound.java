package com.example.drivebox.drivebox.exeptions;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String id) {
        super("File: " + id + " does not exist");

    }
}