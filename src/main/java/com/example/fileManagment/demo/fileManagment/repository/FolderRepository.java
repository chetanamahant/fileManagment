package com.example.fileManagment.demo.fileManagment.repository;

import com.example.fileManagment.demo.fileManagment.entity.Folder;
import com.example.fileManagment.demo.fileManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByParentId(Long parentId);

    List<Folder> findByOwnerAndDeletedFalse(User owner);

    // Root folders
    List<Folder> findByParentIsNullAndDeletedFalse();

    // Child folders
    List<Folder> findByParentIdAndDeletedFalse(Long parentId);
}
