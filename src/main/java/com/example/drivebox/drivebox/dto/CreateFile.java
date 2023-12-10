package com.example.drivebox.drivebox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFile {

    private String fileName;
    private byte[] content;

}
