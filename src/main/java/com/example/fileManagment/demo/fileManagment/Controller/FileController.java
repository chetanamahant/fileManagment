package com.example.fileManagment.demo.fileManagment.Controller;


import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileSharedRenameResponseDto;
import com.example.fileManagment.demo.fileManagment.dto.response.FileUploadResponse;
import com.example.fileManagment.demo.fileManagment.serviceI.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // ✅ UPLOAD FILE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiSuccessResponse<FileUploadResponse>> upload(
            @RequestParam MultipartFile file,
            @RequestParam Long folderId) {

        FileUploadResponse uploadResponse = fileService.upload(file, folderId);

        ApiSuccessResponse<FileUploadResponse> response =
                new ApiSuccessResponse<>(
                        true,
                        "File uploaded successfully",
                        uploadResponse,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ STAR FILE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/starFile/{id}/star")
    public ResponseEntity<ApiSuccessResponse<Void>> star(
            @PathVariable Long id) {

        fileService.starFile(id);

        ApiSuccessResponse<Void> response =
                new ApiSuccessResponse<>(
                        true,
                        "File starred successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE FILE (Soft Delete)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteFile(
            @PathVariable Long id) {

        fileService.deleteFile(id);

        ApiSuccessResponse<Void> response =
                new ApiSuccessResponse<>(
                        true,
                        "File deleted successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ GET FILE BY ID
    @GetMapping("/getFileById/{id}")
    public ResponseEntity<ApiSuccessResponse<FileResponse>> getFileById(
            @PathVariable Long id) {

        FileResponse file = fileService.getFileById(id);

        ApiSuccessResponse<FileResponse> response =
                new ApiSuccessResponse<>(
                        true,
                        "File fetched successfully",
                        file,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ GET ALL FILES (Non-deleted)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllFiles")
    public ResponseEntity<ApiSuccessResponse<List<FileResponse>>> getAll() {

        List<FileResponse> files = fileService.getAllFiles();

        ApiSuccessResponse<List<FileResponse>> response =
                new ApiSuccessResponse<>(
                        true,
                        "Files fetched successfully",
                        files,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ RENAME FILE (Shared)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rename/{fileShareId}")
    public ResponseEntity<ApiSuccessResponse<FileSharedRenameResponseDto>> renameFile(
            @PathVariable Long fileShareId,
            @RequestParam Long fileId,
            @RequestParam Long userId,
            @RequestParam String newName) {

        FileSharedRenameResponseDto dto =
                fileService.renameFile(fileId, userId, newName, fileShareId);

        ApiSuccessResponse<FileSharedRenameResponseDto> response =
                new ApiSuccessResponse<>(
                        true,
                        "File renamed successfully",
                        dto,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    // ✅ SEARCH FILES
    @GetMapping("/search")
    public ResponseEntity<ApiSuccessResponse<List<FileResponse>>> searchFiles(
            @RequestParam String keyword) {

        List<FileResponse> returnData = fileService.searchFiles(keyword);

        ApiSuccessResponse<List<FileResponse>> response =
                new ApiSuccessResponse<>(
                        true,
                        "Files fetched successfully",
                        returnData,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }
}
