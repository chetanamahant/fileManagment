package com.example.fileManagment.demo.fileManagment.serviceimpl;

import com.example.fileManagment.demo.fileManagment.dto.request.foldercreate;
import com.example.fileManagment.demo.fileManagment.dto.response.FolderResponse;
import com.example.fileManagment.demo.fileManagment.entity.Archive;
import com.example.fileManagment.demo.fileManagment.entity.Folder;
import com.example.fileManagment.demo.fileManagment.entity.User;
import com.example.fileManagment.demo.fileManagment.exception.ResourceNotFoundException;
import com.example.fileManagment.demo.fileManagment.repository.ArchiveRepository;
import com.example.fileManagment.demo.fileManagment.repository.FolderRepository;
import com.example.fileManagment.demo.fileManagment.repository.UserRepository;
import com.example.fileManagment.demo.fileManagment.serviceI.FolderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {


    private  final FolderRepository folderRepo;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private final ArchiveRepository archiveRepository;

    // ✅ CREATE folder
    @Override
    public FolderResponse createFolder(foldercreate request) {

        Folder folder = new Folder();
        folder.setName(request.getName());

        if (request.getParentId() != null) {
            Folder parent = folderRepo.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent folder not found"));
            folder.setParent(parent);
        }
        if (request.getOwnerId() != null) {
            User user = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User   not found"));


            folder.setOwner(user);
        }

        Folder saved = folderRepo.save(folder);
        return mapper.map(saved, FolderResponse.class);
    }

    // ✅ GET folders (root or children)
    @Override
    public List<FolderResponse> getFolders(Long parentId) {

        List<Folder> folders;

        if (parentId == null) {
            folders = folderRepo.findByParentIsNullAndDeletedFalse();
        } else {
            folders = folderRepo.findByParentIdAndDeletedFalse(parentId);
        }

        return folders.stream()
                .map(folder -> mapper.map(folder, FolderResponse.class))
                .toList();
    }

    // ✅ UPDATE folder name / parent
    @Override
    public FolderResponse updateFolder(Long id, foldercreate request) {

        Folder folder = folderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        folder.setName(request.getName());

        if (request.getParentId() != null) {
            Folder parent = folderRepo.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setParent(parent);
        }

        Folder updated = folderRepo.save(folder);
        return mapper.map(updated, FolderResponse.class);
    }

    // ✅ SOFT DELETE folder
    @Override
    @Transactional
    public void deleteFolder(Long id) {

        // 1️⃣ Find folder
        Folder folder = folderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        // 2️⃣ Delete folder (if this fails → transaction rollback)
        folderRepo.delete(folder);

        // 3️⃣ Save to archive ONLY after successful delete
        Archive archive = new Archive();
        archive.setName(folder.getName());
        archive.setDetails("Folder deleted");

        archiveRepository.save(archive);
    }

    // ✅ RESTORE folder (undo soft delete)

    @Override
    public void restoreFolder(Long folderId) {
        Folder folder = folderRepo.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        folder.setDeleted(false);
        folderRepo.save(folder);
    }


}
