package com.example.library.Entity.Controller;

import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.LoginDTO;
import com.example.library.Entity.DTO.UserDTO;
import com.example.library.Entity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse = userService.login(loginDTO);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }
    @PostMapping("/update")
    public HttpEntity<?> update(@RequestBody UserDTO userDTO){
        ApiResponse apiResponse = userService.update(userDTO);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }
}
