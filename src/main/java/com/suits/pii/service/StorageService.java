package com.suits.pii.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.suits.pii.dto.StoredFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    private final Bucket bucket;

    public StorageService(Bucket bucket) {
        this.bucket = bucket;
    }

    public UUID uploadFile(final MultipartFile file) throws IOException {
        UUID avatarId = UUID.randomUUID();
        bucket.create(avatarId.toString(), file.getInputStream(), file.getContentType());

        return avatarId;
    }

    public StoredFile getFile(final String filename) {
        Blob blob = bucket.get(filename);

        return StoredFile.builder()
                .contentType(blob.getContentType())
                .file(downloadFile(blob))
                .size(blob.getSize())
                .build();
    }

    private byte[] downloadFile(Blob blob) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.downloadTo(outputStream);
        return outputStream.toByteArray();
    }
}
