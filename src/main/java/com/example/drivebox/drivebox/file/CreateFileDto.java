package com.example.drivebox.drivebox.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFileDto {

    private String fileName;
    private byte[] content;

}
