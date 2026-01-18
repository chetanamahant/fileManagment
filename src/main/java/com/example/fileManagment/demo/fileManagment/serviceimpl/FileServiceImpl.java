package com.example.fileManagment.demo.fileManagment.serviceimpl;

import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.FileSharedRenameResponseDto;
import com.example.fileManagment.demo.fileManagment.dto.response.FileUploadResponse;
import com.example.fileManagment.demo.fileManagment.entity.*;
import com.example.fileManagment.demo.fileManagment.exception.ResourceNotFoundException;
import com.example.fileManagment.demo.fileManagment.repository.ArchiveRepository;
import com.example.fileManagment.demo.fileManagment.repository.FileRepository;
import com.example.fileManagment.demo.fileManagment.repository.FolderRepository;
import com.example.fileManagment.demo.fileManagment.repository.ShareRepository;
import com.example.fileManagment.demo.fileManagment.serviceI.FileService;
import com.example.fileManagment.demo.fileManagment.serviceI.SharesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class FileServiceImpl implements FileService {

    private final FileRepository fileRepo;
    private final FolderRepository folderRepo;
    private final ModelMapper mapper;
    private final SharesService shareService;
    private final ShareRepository shareRepository;

    private final ArchiveRepository archiveRepository;

    private static final String BASE_PATH = System.getProperty("user.home") + "/filemanagment/uploads/";

    // ✅ UPLOAD FILE
    @Override
    public FileUploadResponse upload(MultipartFile file, Long folderId) {

        Folder folder = folderRepo.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Unable to create upload directory");
                }
            }
            String filePath = BASE_PATH + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            FileEntity entity = new FileEntity();
            entity.setFileName(file.getOriginalFilename());
            entity.setContentType(file.getContentType());
            entity.setSize(file.getSize());
            entity.setStoragePath(filePath);
            entity.setFolder(folder);
            entity.setUploadedAt(LocalDateTime.now());

            FileEntity saved = fileRepo.save(entity);

            return new FileUploadResponse(
                    saved.getId(),
                    saved.getFileName()
            );

        } catch (IOException e) {
            e.printStackTrace(); // 👈 VERY IMPORTANT
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // ✅ STAR FILE
    @Override
    public void starFile(Long id) {

        FileEntity file = fileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        file.setStarred(true);
        fileRepo.save(file);
    }

    // ✅ SOFT DELETE FILE
    @Override
    @Transactional
    public void deleteFile(Long id) {

        FileEntity file = fileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));



        fileRepo.delete(file);

        Archive deleteData = new Archive();
        deleteData.setName(file.getFileName());
        deleteData.setDetails(file.getStoragePath());

        archiveRepository.save(deleteData);

    }

    // ✅ GET FILE BY ID
    @Override
    public FileResponse getFileById(Long id) {

        FileEntity file = fileRepo.findById(id)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new RuntimeException("File not found"));

        return mapper.map(file, FileResponse.class);
    }

    // ✅ GET ALL FILES (NON-DELETED)
    @Override
    public List<FileResponse> getAllFiles() {

        return fileRepo.findByDeletedFalse()
                .stream()
                .map(file -> mapper.map(file, FileResponse.class))
                .toList();
    }


    @Override
    public void restoreFile(Long id) {
        FileEntity file = fileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        file.setDeleted(false);
        fileRepo.save(file);
    }


    @Override
    public FileResponse viewFile(Long fileId, Long userId,Long shareId) {

        // permission check
        shareService.getUserAccess(fileId, userId,shareId);

        FileEntity file = fileRepo.findById(fileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("File not found"));

        return mapper.map(file, FileResponse.class);
    }

    // ✏️ RENAME FILE (EDITOR ONLY)
    @Override
    public FileSharedRenameResponseDto renameFile(
            Long fileId,
            Long userId,
            String newName,
            Long shareId) {

        // 🔐 editor permission check
        shareService.checkEditorAccess(fileId, userId, shareId);

        FileEntity file = fileRepo.findById(fileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("File not found"));

        file.setFileName(newName);
        FileEntity savedFile = fileRepo.save(file);

        Shares share = shareRepository.findById(shareId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Share not found"));

        // ✅ Convert Entity → DTO
        FileResponse fileResponse = FileResponse.builder()
                .id(savedFile.getId())
                .fileName(savedFile.getFileName())
                .starred(savedFile.isStarred())
                .build();

        return FileSharedRenameResponseDto.builder()
                .userId(userId)
                .fileId(fileId)
                .shareRole(share.getShareRole())
                .fileData(fileResponse)
                .build();
    }

    @Override
    public List<FileResponse> searchFiles(String keyword) {

//        User owner = new User();
//        owner.setId(userId); // no DB hit needed

        List<FileEntity> files = fileRepo.findByKeyword(keyword);

        return files.stream() .map(file ->
                mapper.map(file, FileResponse.class)) .toList();
    }


}
