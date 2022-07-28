package com.example.library.Entity.Repository;

import com.example.library.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
}
