package com.example.fileManagment.demo.fileManagment.Controller;

import com.example.fileManagment.demo.fileManagment.dto.request.PublicLinkRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.PublicLinkResponse;
import com.example.fileManagment.demo.fileManagment.serviceI.PublicLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicLinkController {

    private final PublicLinkService publicLinkService;

    // 🔗 CREATE PUBLIC LINK
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create-link")
    public ResponseEntity<ApiSuccessResponse<PublicLinkResponse>> createLink(
            @RequestBody PublicLinkRequest request) {

        PublicLinkResponse linkResponse =
                publicLinkService.createPublicLink(request);

        ApiSuccessResponse<PublicLinkResponse> response =
                new ApiSuccessResponse<>(
                        true,
                        "Public link created successfully",
                        linkResponse,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // 🌍 ACCESS FILE VIA PUBLIC LINK (BINARY RESPONSE)

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/files/{token}")
    public ResponseEntity<byte[]> accessFile(
            @PathVariable String token,
            @RequestParam(required = false) String password) {

        byte[] data =
                publicLinkService.accessPublicFile(token, password);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(data);
    }
}

