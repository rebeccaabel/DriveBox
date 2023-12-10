package com.example.drivebox.drivebox.exeptions;

public class FolderNotFound extends RuntimeException{
    public FolderNotFound(String id) {
        super("Folder: " + id + " does not exist");
    }
}
