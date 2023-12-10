package com.example.drivebox.drivebox.services;

import com.example.drivebox.drivebox.dto.CreateUser;
import com.example.drivebox.drivebox.dto.UpdateUser;
import com.example.drivebox.drivebox.entity.User;
import com.example.drivebox.drivebox.exeptions.UserNotFound;
import com.example.drivebox.drivebox.repositroy.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepo userRepository, PasswordEncoder encoder) {
        this.userRepo = userRepository;
        this.encoder = encoder;

    }

    public User createUser(CreateUser dto) {
        User user = new User(dto.getUsername(), encoder.encode(dto.getPassword()), dto.getEmail());
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepo.findById(uuid).orElseThrow(() -> new UserNotFound(id));
    }

    public User updateUser(String id, UpdateUser dto) {
        UUID uuid = UUID.fromString(id);
        User user = userRepo.findById(uuid).orElseThrow(() -> new UserNotFound(id));

        if (dto.getUsername().isPresent()) {
            user.setUsername(dto.getUsername().get());
        }

        if (dto.getEmail().isPresent()) {
            user.setEmail(dto.getEmail().get());
        }
        return userRepo.save(user);
    }


    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepo.findById(uuid).orElseThrow(() -> new UserNotFound(id));
        userRepo.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user '" + username + "'."));
        return user;
    }

}