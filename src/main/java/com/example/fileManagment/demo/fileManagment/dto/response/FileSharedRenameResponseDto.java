package com.example.fileManagment.demo.fileManagment.dto.response;

import com.example.fileManagment.demo.fileManagment.enam.ShareRole;
import com.example.fileManagment.demo.fileManagment.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileSharedRenameResponseDto {

    private Long userId;
    private Long fileId;
    private ShareRole shareRole;
    private FileResponse fileData;   // ✅ DTO ONLY
}
