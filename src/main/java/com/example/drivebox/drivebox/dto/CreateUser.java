package com.example.drivebox.drivebox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUser {
    private String username;
    private String password;
    private String email;

}