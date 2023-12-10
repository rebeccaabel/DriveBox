package com.example.drivebox.drivebox.folder;

import com.example.drivebox.drivebox.file.FileEntity;
import com.example.drivebox.drivebox.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }


    @PostMapping("/folder/create")
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody CreateFolder dto) {
        Folder folder = folderService.createFolder(dto);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }


    @GetMapping("/folder/all")
    public ResponseEntity<List<Folder>> retrieveAllFoldersByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(folderService.getAllFoldersByUser(user), HttpStatus.OK);
    }


    @GetMapping("/folder/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable String id, @AuthenticationPrincipal User user) {
        Folder folder = folderService.getFolderById(id, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }


    @GetMapping("/folder/{id}/files")
    public ResponseEntity<List<FileEntity>> getFilesFromFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        List<FileEntity> files = folderService.getFilesFromFolder(id, user);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }



    @PutMapping("/folder/update/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable String id, @Valid @RequestBody UpdateFolder dto, @AuthenticationPrincipal User user) {
        Folder folder = folderService.updateFolder(id, dto, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }


    @DeleteMapping("/folder/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        folderService.deleteFolder(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
