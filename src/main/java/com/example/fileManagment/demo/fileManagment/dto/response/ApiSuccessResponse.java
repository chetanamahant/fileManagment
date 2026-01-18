package com.example.fileManagment.demo.fileManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiSuccessResponse <T>{
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

}
