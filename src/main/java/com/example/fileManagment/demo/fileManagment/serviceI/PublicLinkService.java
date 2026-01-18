package com.example.fileManagment.demo.fileManagment.serviceI;

import com.example.fileManagment.demo.fileManagment.dto.request.PublicLinkRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.PublicLinkResponse;

public interface PublicLinkService {

    PublicLinkResponse createPublicLink(PublicLinkRequest request);

    byte[] accessPublicFile(String token, String password);
}
