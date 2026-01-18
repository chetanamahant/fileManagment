package com.example.fileManagment.demo.fileManagment.dto.response;

import com.example.fileManagment.demo.fileManagment.enam.ShareRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SharesResponse {

    private Long fileId;
    private String fileName;
    private String sharedWith;
    private ShareRole role;
}
