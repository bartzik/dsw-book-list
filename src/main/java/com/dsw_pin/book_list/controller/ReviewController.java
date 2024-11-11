package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Review;
import com.dsw_pin.book_list.model.User;
import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.repositories.ReviewRepository;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/booklist/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> payload) {
        try {
            String comment = (String) payload.get("comment");
            if (comment == null || comment.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comentário não pode estar vazio");
            }

            String bookIdStr = (String) payload.get("bookId");
            if (bookIdStr == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do livro é obrigatório");
            }
            UUID bookId = UUID.fromString(bookIdStr);

            String userIdStr = (String) payload.get("userId");
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário é obrigatório");
            }
            UUID userId = UUID.fromString(userIdStr);

            Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            Review review = new Review(comment, book, user);
            Review savedReview = reviewRepository.save(review);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato inválido para UUID");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar avaliação");
        }
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBook(@PathVariable UUID bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }
}

