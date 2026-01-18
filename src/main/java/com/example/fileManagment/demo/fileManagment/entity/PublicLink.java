package com.example.fileManagment.demo.fileManagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "public_links")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class PublicLink {

    @Id
    private String token;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity file;

    private LocalDateTime expiryTime;
    private String password; // optional (hashed)
}
