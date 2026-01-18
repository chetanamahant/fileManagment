package com.example.fileManagment.demo.fileManagment.serviceimpl;

import com.example.fileManagment.demo.fileManagment.dto.request.PublicLinkRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.PublicLinkResponse;
import com.example.fileManagment.demo.fileManagment.entity.FileEntity;
import com.example.fileManagment.demo.fileManagment.entity.PublicLink;
import com.example.fileManagment.demo.fileManagment.repository.FileRepository;
import com.example.fileManagment.demo.fileManagment.repository.PublicLinkRepository;
import com.example.fileManagment.demo.fileManagment.serviceI.PublicLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicLinkServiceImpl implements PublicLinkService {

    private final FileRepository fileRepository;
    private final PublicLinkRepository publicLinkRepository;

    private static final String BASE_URL = "http://localhost:9095/public/files/";

    @Override
    public PublicLinkResponse createPublicLink(PublicLinkRequest request) {

        FileEntity file = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new RuntimeException("File not found"));

        String token = UUID.randomUUID().toString();

        PublicLink link = PublicLink.builder()
                .token(token)
                .file(file)
                .expiryTime(request.getExpiryTime())
                .password(request.getPassword()) // later hash
                .build();

        publicLinkRepository.save(link);

        return new PublicLinkResponse(
                BASE_URL + token,
                link.getExpiryTime()
        );
    }

    @Override
    public byte[] accessPublicFile(String token, String password) {

        PublicLink link = publicLinkRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid public link"));

        // 🔒 Expiry check
        if (link.getExpiryTime() != null &&
                LocalDateTime.now().isAfter(link.getExpiryTime())) {
            throw new RuntimeException("Link expired");
        }

        // 🔒 Password check (optional)
        if (link.getPassword() != null &&
                !link.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        try {
            return Files.readAllBytes(Path.of(link.getFile().getStoragePath()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file");
        }
    }
}