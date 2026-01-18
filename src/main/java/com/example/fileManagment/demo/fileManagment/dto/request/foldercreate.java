package com.example.fileManagment.demo.fileManagment.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class foldercreate {
    private String name;
    private Long parentId;
    private Long ownerId;
}
