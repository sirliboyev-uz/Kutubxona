package com.example.library.Entity.Repository;

import com.example.library.Entity.Enums.RoleName;
import com.example.library.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByRoles(RoleName roles);
}
