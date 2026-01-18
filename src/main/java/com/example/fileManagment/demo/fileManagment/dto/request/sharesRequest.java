package com.example.fileManagment.demo.fileManagment.dto.request;

import com.example.fileManagment.demo.fileManagment.enam.ShareRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class sharesRequest {

    private Long fileId;
    private Long userId;
    private ShareRole role; // VIEWER / EDITOR
}
