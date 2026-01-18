package com.example.fileManagment.demo.fileManagment.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LoginRequest {

    private String username;
    private String password;
}
