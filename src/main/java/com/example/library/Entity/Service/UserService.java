package com.example.library.Entity.Service;

import com.example.library.Entity.DTO.ApiResponse;
import com.example.library.Entity.DTO.LoginDTO;
import com.example.library.Entity.DTO.UserDTO;
import com.example.library.Entity.Enums.RoleName;
import com.example.library.Entity.Repository.UserRepository;
import com.example.library.Entity.Users;
import com.example.library.Entity.WebService.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Token token;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username + " not found username"));
    }

    public ApiResponse login(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        Users users= (Users) authenticate.getPrincipal();
        String token1=token.getToken(users.getUsername(), users.getRoles());
        return new ApiResponse(token1, true);
    }
    public ApiResponse update(UserDTO userDTO){
        Optional<Users> byUsername = userRepository.findByRoles(RoleName.ADMIN);
        if (byUsername.isPresent()){
            Users users=byUsername.get();
            users.setFullName(userDTO.getFullName());
            users.setUsername(userDTO.getUsername());
            users.setPassword(userDTO.getPassword());
            userRepository.save(users);
            return new ApiResponse("Yangilandi", true);
        }
        return new ApiResponse("Topilmadi", false);
    }
}
