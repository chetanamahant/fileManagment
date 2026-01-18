package com.example.fileManagment.demo.fileManagment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PublicLinkRequest {
    private Long fileId;
    private LocalDateTime expiryTime; // optional
    private String password; // optional
}
