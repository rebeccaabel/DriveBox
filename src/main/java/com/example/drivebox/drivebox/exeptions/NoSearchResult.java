package com.example.drivebox.drivebox.exeptions;

public class NoSearchResult extends RuntimeException{
    public NoSearchResult(String search) {
        super("No search result" + search);
    }
}