package com.epam.jpop.libraryservice.exception;

public class LibraryException extends RuntimeException {

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryException() {
    }

    public LibraryException(String message) {
        super(message);
    }
}
