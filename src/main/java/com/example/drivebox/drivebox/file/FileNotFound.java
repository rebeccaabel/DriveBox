package com.example.drivebox.drivebox.file;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String id) {
        super("File: " + id + " does not exist");

    }
}