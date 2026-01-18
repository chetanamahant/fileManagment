package com.example.fileManagment.demo.fileManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String fileName;
}
