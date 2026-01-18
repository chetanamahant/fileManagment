package com.example.fileManagment.demo.fileManagment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private long size;
    private String storagePath;

    @ManyToOne
    private Folder folder;

    @ManyToOne
    private User owner;

    private boolean starred = false;
    private boolean deleted = false;

    private LocalDateTime uploadedAt;
}
