package com.suits.pii.controller;

import com.suits.pii.dto.StoredFile;
import com.suits.pii.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController

public class PIIController {

    private final StorageService storageService;

    public PIIController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(name = "/avatar")
    public void uploadAvatar(@RequestParam("avatar") final MultipartFile file) throws IOException {
        UUID uuid = storageService.uploadFile(file);
        System.out.println("Avatar uuid is " + uuid);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable final String fileName) {
        StoredFile file = storageService.getFile(fileName);
        return ResponseEntity.ok()
                .contentLength(file.getSize())
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getFile());
    }
}
