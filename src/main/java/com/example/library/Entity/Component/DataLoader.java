package com.example.library.Entity.Component;
import com.example.library.Entity.Repository.UserRepository;
import com.example.library.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.library.Entity.Enums.RoleName.*;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;


    @Value(value = "${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("Successfully runner");
        if (initMode.equals("always")){
            Users users=new Users("Admin", "admin@gmail.com", passwordEncoder.encode("admin"), ADMIN, true);
            userRepository.save(users);
            Users users2=new Users("User", "user@gmail.com", passwordEncoder.encode("user"), USER, true);
            userRepository.save(users2);
        }
    }
}
