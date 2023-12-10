package com.example.drivebox.drivebox.repositroy;

import com.example.drivebox.drivebox.entity.File;
import com.example.drivebox.drivebox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileRepo extends JpaRepository<File, UUID> {
    List<File> findByUser(User user);
    Optional<File> findByIdAndUser(UUID id, User user);
}