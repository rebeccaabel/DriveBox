package com.example.drivebox.drivebox.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String name;
    private String url;
    private String type;
    private long size;
    private String folderName;
    private String userName;

    public Response(String name, String url, String type, long size, String folderName, String userName) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.folderName = folderName;
        this.userName = userName;
    }
}
