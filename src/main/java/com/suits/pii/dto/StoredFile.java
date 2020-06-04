package com.suits.pii.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoredFile {
    private String contentType;
    private Long size;
    private byte[] file;
}
