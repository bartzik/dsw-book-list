package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
