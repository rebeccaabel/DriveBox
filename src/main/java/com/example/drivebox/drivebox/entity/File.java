package com.example.drivebox.drivebox.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String name;

    private String type;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public File() {
    }

    public File(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
