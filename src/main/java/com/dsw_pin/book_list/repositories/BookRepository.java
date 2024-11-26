package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.reviews WHERE b.id = :bookId")
    Optional<Book> findByIdWithReviews(@Param("bookId") UUID bookId);

    @Query("SELECT b FROM Book b " +
            "JOIN b.authors a " +
            "JOIN b.publisher p " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooks(@Param("query") String query);



}
