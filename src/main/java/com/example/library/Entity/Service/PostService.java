package com.example.library.Entity.Service;

import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.PostDTO;
import com.example.library.Entity.Post;
import com.example.library.Entity.Repository.PostRepository;
import com.example.library.Entity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Value(value = "${spring.datasource.url}")
    String initMode;
    public ApiResponse add(PostDTO postDTO){
        Optional<Post> optionalPost = postRepository.findByTitle(postDTO.getTitle());
        if (!optionalPost.isPresent()){
            Post post=new Post();
            post.setTitle(postDTO.getTitle());
            post.setText(postDTO.getText());
            post.setUrl(initMode+"/"+postDTO.getTitle());
            postRepository.save(post);
            return new ApiResponse("Xabar qo'shildi", true);
        }
        return new ApiResponse("Xabar allaqachon yaratilgan", false);
    }

    public ApiResponse update(Long id, PostDTO postDTO) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()){
            Post post=byId.get();
            post.setTitle(postDTO.getTitle());
            post.setText(postDTO.getText());
            post.setUrl(initMode+"/"+postDTO.getTitle());
            return new ApiResponse("Xabar yangilandi",true);
        }
        return new ApiResponse("Bunday xabar mavjud emas", false);
    }
    public ApiResponse delete(Long id){
        if (postRepository.existsById(id)){
            postRepository.deleteById(id);
            return new ApiResponse("Xabar o'chirildi", true);
        }
        return new ApiResponse("Bunday xabar mavjud emas!",false);
    }

    public ApiResponse read(Long id){
        Optional<Post> byId = postRepository.findById(id);
        return byId.map(post -> new ApiResponse("Xabarlar", true, post)).orElseGet(()->new ApiResponse("Post topilmadi", false));
    }
    public ApiResponse readAll(){
        List<Post> postList=postRepository.findAll();
        return new ApiResponse("Xabarlar barchasi", true, postList);
    }
}
