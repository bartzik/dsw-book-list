package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.model.Author;
import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.model.Publisher;
import com.dsw_pin.book_list.repositories.AuthorRepository;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.repositories.PublisherRepository;
import com.dsw_pin.book_list.dtos.BookRecordDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;

    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(UUID id){
        return bookRepository.findById(id);
    }

    @Transactional
    public Book saveBook(BookRecordDto bookRecordDto) {
        Book book = new Book();
        book.setTitle(bookRecordDto.title());

        // Verificação se Publisher existe
        Publisher publisher = publisherRepository.findById(bookRecordDto.publisherId())
                .orElseThrow(() -> new EntityNotFoundException("Editora não encontrada."));
        book.setPublisher(publisher);

        // Verificação se os autores existem
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookRecordDto.authorIds()));
        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Nenhum autor encontrado com os IDs fornecidos.");
        }
        book.setAuthors(authors);

        // Verificação do publicationYear
        if (bookRecordDto.publicationYear() <= 0) {
            throw new IllegalArgumentException("O ano de publicação deve ser maior que 0.");
        }
        book.setPublicationYear(bookRecordDto.publicationYear());

        // Adiciona a URL da foto
        book.setPhotoUrl(bookRecordDto.photoUrl());

        return bookRepository.save(book);
    }


    @Transactional
    public void deleteBook(UUID id){
        bookRepository.deleteById(id);
    }

    public String savePhoto(MultipartFile file) throws Exception {
        // Cria um nome único para o arquivo
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Path.of("uploads/"); // Diretório onde as fotos são salvas

        // Cria o diretório se ele não existir
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Salva o arquivo no diretório
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retorna o caminho relativo ou a URL pública do arquivo
        return "/uploads/" + fileName;
    }


}
