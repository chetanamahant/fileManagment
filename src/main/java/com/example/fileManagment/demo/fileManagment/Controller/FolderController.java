package com.example.fileManagment.demo.fileManagment.Controller;

import com.example.fileManagment.demo.fileManagment.dto.request.foldercreate;
import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FolderResponse;
import com.example.fileManagment.demo.fileManagment.serviceI.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor

public class FolderController {

    private final FolderService folderService;


        // ✅ CREATE FOLDER
        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<ApiSuccessResponse<FolderResponse>> create(
                @RequestBody foldercreate request) {

            FolderResponse folder = folderService.createFolder(request);

            ApiSuccessResponse<FolderResponse> response =
                    new ApiSuccessResponse<>(
                            true,
                            "Folder created successfully",
                            folder,
                            LocalDateTime.now()
                    );

            return ResponseEntity.ok(response);
        }

        // ✅ GET FOLDERS (ROOT / CHILD)
        @GetMapping("/GetFolderByid/{id}")
        public ResponseEntity<ApiSuccessResponse<List<FolderResponse>>> list(
                @RequestParam(required = false) Long parentId) {

            List<FolderResponse> folders = folderService.getFolders(parentId);

            ApiSuccessResponse<List<FolderResponse>> response =
                    new ApiSuccessResponse<>(
                            true,
                            "Folders fetched successfully",
                            folders,
                            LocalDateTime.now()
                    );

            return ResponseEntity.ok(response);
        }

        // ✅ UPDATE FOLDER
        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/update/{id}")
        public ResponseEntity<ApiSuccessResponse<FolderResponse>> update(
                @PathVariable Long id,
                @RequestBody foldercreate request) {

            FolderResponse updatedFolder =
                    folderService.updateFolder(id, request);

            ApiSuccessResponse<FolderResponse> response =
                    new ApiSuccessResponse<>(
                            true,
                            "Folder updated successfully",
                            updatedFolder,
                            LocalDateTime.now()
                    );

            return ResponseEntity.ok(response);
        }

        // ✅ DELETE FOLDER (SOFT DELETE)

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiSuccessResponse<Void>> delete(
                @PathVariable Long id) {

            folderService.deleteFolder(id);

            ApiSuccessResponse<Void> response =
                    new ApiSuccessResponse<>(
                            true,
                            "Folder deleted successfully",
                            null,
                            LocalDateTime.now()
                    );

            return ResponseEntity.ok(response);
        }


}
