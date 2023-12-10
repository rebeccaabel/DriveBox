package com.example.drivebox.drivebox.Controllers;

import com.example.drivebox.drivebox.dto.CreateUser;
import com.example.drivebox.drivebox.dto.UpdateUser;
import com.example.drivebox.drivebox.entity.User;
import com.example.drivebox.drivebox.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }


    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUser dto) {
        User user = userService.createUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }


    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}