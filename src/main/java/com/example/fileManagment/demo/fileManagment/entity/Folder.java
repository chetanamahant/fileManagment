package com.example.fileManagment.demo.fileManagment.entity;

import com.example.fileManagment.demo.fileManagment.enam.FolderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parent;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;


    @Enumerated(EnumType.STRING)
    private FolderStatus status = FolderStatus.ACTIVE;

    // ✅ Soft delete flag
    @Column(nullable = false)
    private boolean deleted = false;
}
