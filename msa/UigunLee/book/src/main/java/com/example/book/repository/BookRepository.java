package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}