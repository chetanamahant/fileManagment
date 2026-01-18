package com.example.fileManagment.demo.fileManagment.Controller;

import com.example.fileManagment.demo.fileManagment.dto.request.sharesRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.SharesResponse;
import com.example.fileManagment.demo.fileManagment.serviceI.SharesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
public class ShareController {

    private final SharesService shareService;

    // 🔐 Only file owner / admin should call this
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/share-file")
    public ResponseEntity<ApiSuccessResponse<SharesResponse>> shareFile(
            @RequestBody sharesRequest request) {

        SharesResponse shareResponse =
                shareService.shareFile(request);

        ApiSuccessResponse<SharesResponse> response =
                new ApiSuccessResponse<>(
                        true,
                        "File shared successfully",
                        shareResponse,
                        LocalDateTime.now()
                );
        return ResponseEntity.ok(response);
    }
}
