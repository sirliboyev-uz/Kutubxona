package com.example.library.Entity.Service;

import com.example.library.Entity.Book;
import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.BookDTO;
import com.example.library.Entity.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    String manzil="C:\\Users\\Sirli\\OneDrive\\Ishchi stol\\lib";
    public ApiResponse addBook(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        if (file!=null && !bookRepository.existsByName(file.getName())){
            Book file1 = new Book();
            file1.setName(file.getName());
            file1.setSize(file.getSize());
            file1.setContentType(file.getContentType());
            String newName = file.getOriginalFilename();
            String[] split = newName.split("\\.");
            String s = file1.getName()+"."+split[split.length-1];
            file1.setNewName(s);
            Path path = Paths.get(manzil+"/"+s);
            Files.copy(file.getInputStream(), path);
            bookRepository.save(file1);
            return new ApiResponse("Kitob joylandi", true);
        }
        return new ApiResponse("Joylanmadi", false);
    }

    public ApiResponse downloadBook(Long id, HttpServletResponse response) throws IOException{
        Optional<Book> fileOptional = bookRepository.findById(id);
        if (fileOptional.isPresent()){
            Book file = fileOptional.get();
            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition","attachment; name="+file.getName());
            FileInputStream fileInputStream = new FileInputStream(manzil+"/"+file.getNewName());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
            return new ApiResponse("Yuklandi",true, fileInputStream);
        }
        return new ApiResponse("yuklanmadi",true);
    }

    public ApiResponse deleteBook(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            Book book=optionalBook.get();

            File file = new File(manzil+"/"+book.getNewName());
            boolean isDeleted = file.delete();
            bookRepository.delete(book);
            if(isDeleted) {
                return new ApiResponse("Kitob o'chirildi", true);
            } else {
                return new ApiResponse("Mavjud emas shekilli", false);
            }
        }
        return new ApiResponse("Bunday kitob mavjud emas", false);
    }

//    public Object readAllBook(HttpServletResponse response) {
//
//    }
}
