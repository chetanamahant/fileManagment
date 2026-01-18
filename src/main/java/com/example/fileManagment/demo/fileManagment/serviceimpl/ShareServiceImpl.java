package com.example.fileManagment.demo.fileManagment.serviceimpl;


import com.example.fileManagment.demo.fileManagment.dto.request.sharesRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.FileResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.SharesResponse;
import com.example.fileManagment.demo.fileManagment.enam.ShareRole;
import com.example.fileManagment.demo.fileManagment.entity.FileEntity;
import com.example.fileManagment.demo.fileManagment.entity.Shares;
import com.example.fileManagment.demo.fileManagment.entity.User;
import com.example.fileManagment.demo.fileManagment.exception.ResourceNotFoundException;
import com.example.fileManagment.demo.fileManagment.exception.UnauthorizedException;
import com.example.fileManagment.demo.fileManagment.repository.FileRepository;
import com.example.fileManagment.demo.fileManagment.repository.ShareRepository;
import com.example.fileManagment.demo.fileManagment.repository.UserRepository;
import com.example.fileManagment.demo.fileManagment.serviceI.SharesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements SharesService {

    private final ShareRepository shareRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Override
    public SharesResponse shareFile(sharesRequest request) {

        FileEntity file = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Shares share = Shares.builder()
                .file(file)
                .sharedWith(user)
                .shareRole(request.getRole())
                .build();

        shareRepository.save(share);

        return SharesResponse.builder()
                .fileId(file.getId())
                .fileName(file.getFileName())
                .sharedWith(user.getUsername())
                .role(request.getRole())
                .build();
    }


    public ShareRole getUserAccess(Long fileId, Long userId,Long shareId) {

        Shares share = shareRepository
                .findByFileIdAndSharedWithId(fileId, userId,shareId)
                .orElseThrow(() ->
                        new UnauthorizedException("No access to this file"));

        return share.getShareRole();
    }

    // 🔐 EDITOR CHECK
    @Override
    public void checkEditorAccess(Long fileId, Long userId ,Long shareId) {

        ShareRole role = getUserAccess(fileId, userId, shareId);

        if (role != ShareRole.EDITOR) {
            throw new UnauthorizedException("Editor access required");
        }
    }

}