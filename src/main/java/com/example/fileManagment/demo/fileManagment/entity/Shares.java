package com.example.fileManagment.demo.fileManagment.entity;

import com.example.fileManagment.demo.fileManagment.enam.ShareRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_shares")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Shares {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FileEntity file;

    @ManyToOne
    private User sharedWith;

    @Enumerated(EnumType.STRING)
    private ShareRole shareRole;
    //= ShareRole. VIEWER;// EDITOR;
}
