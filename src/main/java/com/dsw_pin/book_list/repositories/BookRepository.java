package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
