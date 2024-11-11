package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

//    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.reviews WHERE b.id = :bookId")
//    Optional<Book> findByIdWithReviews(@Param("bookId") UUID bookId);


}
