package com.dsw_pin.book_list.controller;


import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.services.BookService;
import dtos.BookRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;


import java.util.List;

@RestController
@RequestMapping("/booklist/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookRecordDto bookRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecordDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID id){
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        }
    }

}
