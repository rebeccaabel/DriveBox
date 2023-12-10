package com.example.drivebox.drivebox.file;

import com.example.drivebox.drivebox.user.User;
import com.example.drivebox.drivebox.messages.Message;
import com.example.drivebox.drivebox.messages.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file/upload")
    public ResponseEntity<Message> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderId") String folderId) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            fileService.store(file, folderId, user);
            String successMessage = "File successfully uploaded: " + file.getOriginalFilename();
            return new ResponseEntity<>(new Message(successMessage), HttpStatus.CREATED);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Access denied: " + e.getMessage()));
        } catch (Exception e) {
            String errorMessage = "Could not upload file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(errorMessage));
        }
    }


    @GetMapping("/files/all")
    public ResponseEntity<List<Response>> retrieveAllFilesByUser(@AuthenticationPrincipal User user) {
        List<Response> files = fileService.getAllFilesByUser(user).stream().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/all/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new Response(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getFolder() != null ? dbFile.getFolder().getFolderName() : null,
                    dbFile.getUser() != null ? dbFile.getUser().getUsername() : null);

        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> retrieveFileById(@PathVariable String id, @AuthenticationPrincipal User user) {
        FileEntity file = fileService.getFileByUser(id, user);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getName());


        String contentType = determineContentType(file.getName());
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);
    }

    private String determineContentType(String fileName) {
        String[] fileParts = fileName.split("\\.");
        if (fileParts.length > 1) {
            String extension = fileParts[fileParts.length - 1].toLowerCase();
            switch (extension) {
                case "pdf":
                    return "application/pdf";
                case "png":
                    return "image/png";
                case "jpg":
                case "jpeg":
                    return "image/jpeg";
                case "svg":
                    return "image/svg+xml";
                case "gif":
                    return "image/gif";
                case "zip":
                    return "application/zip";
                case "txt":
                    return "text/plain";
                case "html":
                    return "text/html";

                default:
                    return "application/octet-stream";
            }
        }
        return "application/octet-stream";
    }



    @DeleteMapping("/files/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFileById(@PathVariable String id, @AuthenticationPrincipal User user) {
        fileService.deleteFileByUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
