package com.example.drivebox.drivebox.folder;

import com.example.drivebox.drivebox.file.FileEntity;
import com.example.drivebox.drivebox.user.User;
import com.example.drivebox.drivebox.user.UserNotFound;
import com.example.drivebox.drivebox.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class FolderService {

    private final FolderRepo folderRepo;
    private final UserRepo userRepo;


    @Autowired
    public FolderService(FolderRepo folderRepo, UserRepo userRepo) {
        this.folderRepo = folderRepo;
        this.userRepo = userRepo;
    }


    public Folder createFolder(CreateFolder dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFound(currentUsername));

        Folder folder = new Folder(dto.getFolderName());
        folder.setUser(currentUser);
        return folderRepo.save(folder);
    }


    public List<Folder> getAllFoldersByUser(User user) {
        return folderRepo.findByUser(user);
    }


    public Folder getFolderById(String id, User user) {
        UUID uuid = UUID.fromString(id);

        Folder folder = folderRepo.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new FolderNotFound(id));

        return folder;
    }



    public List<FileEntity> getFilesFromFolder(String folderId, User user) {
        UUID uuid = UUID.fromString(folderId);
        Folder folder = folderRepo.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new AccessDeniedException("You do not have access to this folder"));

        return folderRepo.findFilesByFolderIdAndUser(uuid, user);
    }


    public Folder updateFolder(String id, UpdateFolder dto, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepo.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFound(id));

        if (dto.getFolderName().isPresent()) {
            folder.setFolderName(dto.getFolderName().get());
        }
        return folderRepo.save(folder);
    }

    public void deleteFolder(String id, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepo.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFound(id));
        folderRepo.delete(folder);
    }
}
