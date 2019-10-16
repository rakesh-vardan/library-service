package com.epam.jpop.libraryservice.controller;

import com.epam.jpop.libraryservice.domain.Book;
import com.epam.jpop.libraryservice.domain.Result;
import com.epam.jpop.libraryservice.domain.User;
import com.epam.jpop.libraryservice.exception.LibraryException;
import com.epam.jpop.libraryservice.feign.BookClient;
import com.epam.jpop.libraryservice.feign.UserClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/lib")
public class LibraryController {

    private static Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private UserClient userClient;

    private final String booksUri = "http://book-service/api/books/";
    private final String usersUri = "http://user-service/api/users/";

    /*API calls to Books micro service
     * Which includes all CRUD operations on Book
     */
    @GetMapping("/books")
    @ApiOperation(value = "View list of all available books in the library", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of books"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource requested"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Object> getAllBooks() {
        logger.info("Getting all the available books from the library");
        ResponseEntity<Object> apiResponse = restTemplate.getForEntity(booksUri, Object.class);
        return new ResponseEntity<>(apiResponse.getBody(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    @ApiOperation(value = "Get a book by an Id")
    public ResponseEntity<Object> getBook(
            @ApiParam(value = "Enter the id of the book to retrieve from library", required = true)
            @PathVariable Long id) {
        logger.info("Fetching the book details with an id from the library: {}", id);
        ResponseEntity<Object> apiResponse = restTemplate.getForEntity(booksUri + id, Object.class);
        return new ResponseEntity<>(apiResponse.getBody(), HttpStatus.OK);
    }

    @PostMapping("/books")
    @ApiOperation(value = "Add a new book to the library")
    public ResponseEntity<Object> addNewBook(
            @ApiParam(value = "New Book details object to save the book information to library", required = true)
            @RequestBody Book book) {
        ResponseEntity<Book> apiResponse = restTemplate.postForEntity(booksUri, book, Book.class);
        logger.info("Successfully added a new book to the library: {}", Objects.requireNonNull(apiResponse.getBody())
                .getId());
        Result result = new Result(apiResponse.getBody().getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    @ApiOperation(value = "Update an existing book from the library")
    public void updateBook(
            @ApiParam(value = "Book Id to Update the details to", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated Book information", required = true) @Valid @RequestBody Book book) {
        if (!book.getId().equals(id)) {
            throw new LibraryException("Unable to find the entity to update");
        }
        HttpEntity httpEntity = new HttpEntity(book);
        restTemplate.exchange(booksUri + id, HttpMethod.PUT, httpEntity, Book.class);
    }

    @DeleteMapping("/books/{id}")
    @ApiOperation(value = "Delete an existing book from the library")
    public void deleteBook(
            @ApiParam(value = "Book Id to delete the data from the library", required = true) @PathVariable Long id) {
        logger.info("Deleting the book from the library: {}", id);
        restTemplate.delete(booksUri + id);
    }

    /*API calls to Users micro service
     * Which includes all CRUD operations on User
     */

    @GetMapping("/users")
    @ApiOperation(value = "View list of all available users in the library", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource requested"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Object> getAllUsers() {
        logger.info("Getting all the available users from the library");
        ResponseEntity<Object> apiResponse = restTemplate.getForEntity(usersUri, Object.class);
        return new ResponseEntity<>(apiResponse.getBody(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get a user by an Id")
    public ResponseEntity<Object> getUser(
            @ApiParam(value = "Enter the id of the user to retrieve from library", required = true)
            @PathVariable Long id) {
        logger.info("Fetching the user details with an id from the library: {}", id);
        ResponseEntity<Object> apiResponse = restTemplate.getForEntity(usersUri + id, Object.class);
        return new ResponseEntity<>(apiResponse.getBody(), HttpStatus.OK);
    }

    @PostMapping("/users")
    @ApiOperation(value = "Add a new user to the library")
    public ResponseEntity<Object> addNewUser(
            @ApiParam(value = "New User details object to save the book information to library")
            @RequestBody User user) {
        ResponseEntity<User> apiResponse = restTemplate.postForEntity(usersUri, user, User.class);
        logger.info("Successfully added a new user to the library: {}", Objects.requireNonNull(apiResponse.getBody()).getId());
        Result result = new Result(apiResponse.getBody().getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "Update an existing user from the library")
    public void updateUser(
            @ApiParam(value = "User Id to Update the details to", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated User information", required = true) @Valid @RequestBody User user) {
        if (!user.getId().equals(id)) {
            throw new LibraryException("Unable to find the entity to update");
        }

        HttpEntity httpEntity = new HttpEntity(user);
        restTemplate.exchange(usersUri + id, HttpMethod.PUT, httpEntity, User.class);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "Delete an existing user from the library")
    public void deleteUser(
            @ApiParam(value = "User Id to delete the data from the library", required = true) @PathVariable Long id) {
        logger.info("Deleting the user from the library: {}", id);
        restTemplate.delete(usersUri + id);
    }

    /*Using Feign clients with version 2*/
    @GetMapping("/books/v2")
    public List<Book> getBooks() {
        return bookClient.getBooks();
    }

    @GetMapping("/books/v2/{id}")
    public Book getSingleBook(@PathVariable Long id) {
        return bookClient.getBook(id);
    }

    @PostMapping("/books/v2")
    public Book addSingleBook(@RequestBody Book book) {
        return bookClient.addBook(book);
    }

    @PutMapping("/books/v2/{id}")
    public Book updateSingleBook(@RequestBody Book book, @PathVariable Long id) {
        return bookClient.updateBook(book, id);
    }

    @DeleteMapping("/books/v2/{id}")
    public Book deleteSingleBook(@PathVariable Long id) {
        return bookClient.deleteBook(id);
    }

    @GetMapping("/users/v2")
    public List<User> getUsers() {
        return userClient.getUsers();
    }

    @GetMapping("/users/v2/{id}")
    public User getSingleUser(@PathVariable Long id) {
        return userClient.getUser(id);
    }

    @PostMapping("/users/v2")
    public User addSingleUser(@RequestBody User user) {
        return userClient.addUser(user);
    }

    @PutMapping("/users/v2/{id}")
    public User updateSingleUser(@RequestBody User user, @PathVariable Long id) {
        return userClient.updateUser(user, id);
    }

    @DeleteMapping("/users/v2/{id}")
    public User deleteSingleUser(@PathVariable Long id) {
        return userClient.deleteUser(id);
    }
}
