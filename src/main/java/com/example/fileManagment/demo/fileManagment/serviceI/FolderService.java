package com.example.fileManagment.demo.fileManagment.serviceI;

import com.example.fileManagment.demo.fileManagment.dto.request.foldercreate;
import com.example.fileManagment.demo.fileManagment.dto.response.FolderResponse;

import java.util.List;

public interface FolderService {

    FolderResponse createFolder(foldercreate request);

    List<FolderResponse> getFolders(Long parentId);

    void deleteFolder(Long folderId);

    void restoreFolder(Long folderId);

    FolderResponse updateFolder(Long folderId, foldercreate request);




}
