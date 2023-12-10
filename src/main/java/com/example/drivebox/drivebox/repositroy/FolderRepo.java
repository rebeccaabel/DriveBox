package com.example.drivebox.drivebox.repositroy;

import com.example.drivebox.drivebox.entity.File;
import com.example.drivebox.drivebox.entity.Folder;
import com.example.drivebox.drivebox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepo extends JpaRepository<Folder, UUID> {
    List<Folder> findByFolderNameContainingIgnoreCaseAndUser(String search, User user);
    List<Folder> findByUser(User user);
    Optional<Folder> findByIdAndUser(UUID id, User user);
    @Query("SELECT f.files FROM Folder f WHERE f.id = :folderId AND f.user = :user")
    List<File> findFilesByFolderIdAndUser(@Param("folderId") UUID folderId, @Param("user") User user);
}
