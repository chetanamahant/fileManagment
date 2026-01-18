package com.example.fileManagment.demo.fileManagment.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ApiResponse<T> {
    public boolean success;
    public String message;
    public T data;
}
