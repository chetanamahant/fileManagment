package com.example.fileManagment.demo.fileManagment.serviceI;

import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileSharedRenameResponseDto;
import com.example.fileManagment.demo.fileManagment.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileUploadResponse upload(MultipartFile file, Long folderId);

    void deleteFile(Long fileId);

    void restoreFile(Long fileId);

    void starFile(Long fileId);

    FileResponse getFileById(Long fileId);

    List<FileResponse> getAllFiles();

    FileResponse viewFile(Long fileId, Long userId,Long shareId);

    List<FileResponse> searchFiles(String keyword);

    FileSharedRenameResponseDto renameFile(Long fileId, Long userId, String newName,Long shareId );
}
