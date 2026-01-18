package com.example.fileManagment.demo.fileManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PublicLinkResponse {

    private String publicUrl;
    private LocalDateTime expiry;
}
