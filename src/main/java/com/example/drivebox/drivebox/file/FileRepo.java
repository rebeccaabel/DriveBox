package com.example.drivebox.drivebox.file;

import com.example.drivebox.drivebox.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileRepo extends JpaRepository<FileEntity, UUID> {
    List<FileEntity> findByUser(User user);
    Optional<FileEntity> findByIdAndUser(UUID id, User user);
}