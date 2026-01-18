package com.example.fileManagment.demo.fileManagment.repository;

import com.example.fileManagment.demo.fileManagment.entity.FileEntity;
import com.example.fileManagment.demo.fileManagment.entity.Shares;
import com.example.fileManagment.demo.fileManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShareRepository  extends JpaRepository<Shares, Long> {

    @Query(value ="select * from file_shares sh where sh.file_id =:fileId and sh.shared_with_id =:userId  and \n" +
            " sh.id =:shareId ", nativeQuery =true)
      Optional<Shares> findByFileIdAndSharedWithId(Long fileId, Long userId ,Long shareId);

    boolean existsByFileIdAndSharedWithId(Long fileId, Long userId);
}
