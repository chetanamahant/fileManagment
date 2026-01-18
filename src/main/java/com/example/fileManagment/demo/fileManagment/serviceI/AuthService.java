package com.example.fileManagment.demo.fileManagment.serviceI;

import com.example.fileManagment.demo.fileManagment.dto.request.RegisterRequest;
import com.example.fileManagment.demo.fileManagment.dto.request.LoginRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.AuthResponse;
import com.example.fileManagment.demo.fileManagment.dto.response.UserResponse;
import com.example.fileManagment.demo.fileManagment.entity.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    User register(RegisterRequest request);


}
