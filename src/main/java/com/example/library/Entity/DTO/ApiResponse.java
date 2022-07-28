package com.example.library.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private boolean type;
    private Object object;

    public ApiResponse(String message, Boolean type) {
        this.message = message;
        this.type = type;
    }
}
