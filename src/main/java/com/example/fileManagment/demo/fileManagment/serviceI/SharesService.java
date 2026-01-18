package com.example.fileManagment.demo.fileManagment.serviceI;

import com.example.fileManagment.demo.fileManagment.dto.request.sharesRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.PublicLinkResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.SharesResponse;
import com.example.fileManagment.demo.fileManagment.enam.ShareRole;

import java.time.LocalDateTime;

public interface SharesService {
    SharesResponse shareFile(sharesRequest request);

    ShareRole getUserAccess(Long fileId, Long userId,Long shareId);

    void checkEditorAccess(Long fileId, Long userId,Long shareId);
}
