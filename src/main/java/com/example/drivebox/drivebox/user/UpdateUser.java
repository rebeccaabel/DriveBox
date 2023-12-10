package com.example.drivebox.drivebox.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class UpdateUser {

    private Optional<String> username;

    @Email(message = "Invalid email format")
    private Optional<String> email;

}