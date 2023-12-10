package com.example.drivebox.drivebox.services;

import com.example.drivebox.drivebox.entity.File;
import com.example.drivebox.drivebox.entity.Folder;
import com.example.drivebox.drivebox.entity.User;
import com.example.drivebox.drivebox.exeptions.FolderNotFound;
import com.example.drivebox.drivebox.exeptions.FileNotFound;
import com.example.drivebox.drivebox.repositroy.FileRepo;
import com.example.drivebox.drivebox.repositroy.FolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private final FolderRepo folderRepo;
    private final FileRepo fileRepo;
    @Autowired
    public FileService(FolderRepo folderRepo, FileRepo fileRepo) {
        this.folderRepo = folderRepo;
        this.fileRepo = fileRepo;
    }

    @Transactional
    public void store(MultipartFile file, String folderId, User user) throws IOException {
        UUID folderUUID = UUID.fromString(folderId);
        Folder folder = folderRepo.findById(folderUUID)
                .orElseThrow(() -> new FolderNotFound(folderId));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to upload files to this folder");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());
        fileDB.setFolder(folder);
        fileDB.setUser(user);
        fileRepo.save(fileDB);
    }


    @Transactional
    public List<File> getAllFilesByUser(User user) {
        return fileRepo.findByUser(user);
    }


    @Transactional
    public File getFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        return fileRepo.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFound(id));
    }

    @Transactional
    public void deleteFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        File file = fileRepo.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFound(id));
        fileRepo.delete(file);
    }
}