package com.gmail.creativegeeksuresh.ishare.exception;

public class BookAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
