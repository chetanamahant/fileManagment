package com.example.fileManagment.demo.fileManagment.Controller;

import com.example.fileManagment.demo.fileManagment.dto.request.RegisterRequest;
import com.example.fileManagment.demo.fileManagment.dto.request.LoginRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.ApiSuccessResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.AuthResponse;
import com.example.fileManagment.demo.fileManagment.entity.User;
import com.example.fileManagment.demo.fileManagment.serviceI.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/apiauth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse> register(@RequestBody RegisterRequest request) {


        User data = authService.register(request);


        return ResponseEntity.ok( new ApiSuccessResponse<>(
                true,
                "Signup successfully",
                data,
                LocalDateTime.now()
        ));
    }


}
