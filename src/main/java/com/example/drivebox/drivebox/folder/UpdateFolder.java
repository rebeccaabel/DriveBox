package com.example.drivebox.drivebox.folder;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class UpdateFolder {
    private Optional<String> folderName;
}