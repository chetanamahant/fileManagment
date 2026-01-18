package com.example.fileManagment.demo.fileManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderResponse {
    private Long id;
    private String name;
    private boolean starred;
}
