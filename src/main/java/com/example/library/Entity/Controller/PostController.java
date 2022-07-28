package com.example.library.Entity.Controller;

import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.PostDTO;
import com.example.library.Entity.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody PostDTO postDTO){
        ApiResponse apiResponse=postService.add(postDTO);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody PostDTO postDTO){
        ApiResponse apiResponse = postService.update(id, postDTO);
        return ResponseEntity.status(apiResponse.isType()?201:409).body(apiResponse);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse=postService.delete(id);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Long id){
        ApiResponse apiResponse=postService.read(id);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        ApiResponse apiResponse=postService.readAll();
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }
}
