package com.example.library.Entity.Controller;

import com.example.library.Entity.Book;
import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.BookDTO;
import com.example.library.Entity.Repository.BookRepository;
import com.example.library.Entity.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    String manzil="C:\\Users\\Sirli\\OneDrive\\Ishchi stol\\lib";
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public HttpEntity<?> upload(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = bookService.addBook(request);
        return ResponseEntity.status(apiResponse.isType()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(bookService.downloadBook(id, response));
    }

//    @GetMapping()
//    public HttpEntity<?> readAll(HttpServletResponse response) throws IOException {
//        return ResponseEntity.ok(bookService.readAllBook(response));
//    }

}
