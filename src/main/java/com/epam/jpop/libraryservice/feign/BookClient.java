package com.epam.jpop.libraryservice.feign;

import com.epam.jpop.libraryservice.domain.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/books")
    List<Book> getBooks();

    @GetMapping("/api/books/{id}")
    Book getBook(@PathVariable Long id);

    @PostMapping("/api/books")
    Book addBook(@RequestBody Book book);

    @PutMapping("/api/books/{id}")
    Book updateBook(@RequestBody Book book, @PathVariable Long id);

    @DeleteMapping("/api/books/{id}")
    Book deleteBook(@PathVariable Long id);

}
