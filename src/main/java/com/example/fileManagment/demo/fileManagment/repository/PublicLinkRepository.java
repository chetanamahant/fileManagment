package com.example.fileManagment.demo.fileManagment.repository;

import com.example.fileManagment.demo.fileManagment.entity.PublicLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicLinkRepository extends JpaRepository<PublicLink, String> {

    Optional<PublicLink> findByToken(String token);
}

