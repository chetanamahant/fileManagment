package com.example.fileManagment.demo.fileManagment.repository;

import com.example.fileManagment.demo.fileManagment.entity.FileEntity;
import com.example.fileManagment.demo.fileManagment.entity.Folder;
import com.example.fileManagment.demo.fileManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByFolderAndDeletedFalse(Folder folder);

    List<FileEntity> findByOwnerAndDeletedFalse(User owner);

    @Query(value ="select * from files fl where fl.content_type =:keyword " +
            "OR fl.file_name =:keyword " ,nativeQuery = true)
    List<FileEntity> findByKeyword(String keyword);

    List<FileEntity> findByDeletedFalse();


    Optional<FileEntity> findByIdAndDeletedFalse(Long id);



}
