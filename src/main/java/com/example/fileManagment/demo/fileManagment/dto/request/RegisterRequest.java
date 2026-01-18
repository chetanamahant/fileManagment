package com.example.fileManagment.demo.fileManagment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String name;
    private String username;
    private String password;
    private String email;
    private String role;

}
