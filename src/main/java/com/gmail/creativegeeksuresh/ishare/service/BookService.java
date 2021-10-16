package com.gmail.creativegeeksuresh.ishare.service;

import java.util.ArrayList;
import java.util.List;

import com.gmail.creativegeeksuresh.ishare.dto.BookDto;
import com.gmail.creativegeeksuresh.ishare.exception.BookAlreadyExistsException;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidBookException;
import com.gmail.creativegeeksuresh.ishare.model.Book;
import com.gmail.creativegeeksuresh.ishare.repository.BookRepository;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CustomUtils customUtils;

    public Book addBook(BookDto request) throws BookAlreadyExistsException, Exception {

        if (bookRepo.findByTitle(request.getTitle()) != null)
            throw new BookAlreadyExistsException("Book with same title already exists");

        Book newBook = new Book();
        newBook.setTitle(request.getTitle());
        newBook.setAuthor(request.getAuthor());
        newBook.setDescription(request.getDescription());
        newBook.setAvailable(request.getAvailable());
        newBook.setPublishedYear(request.getPublishedYear());
        newBook.setUid(customUtils.generateToken());
        newBook.setLocation(request.getLocation());
        return bookRepo.save(newBook);
    }

    public List<Book> getAllBooks() {
        try {
            return (List<Book>) bookRepo.findAll();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public List<Book> getAllUnavilableBooks() {
        try {
            return (List<Book>) bookRepo.findByAvailable(Boolean.FALSE);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public void deleteBookByUid(String uid) throws InvalidBookException, Exception {
        Book book = bookRepo.findByUid(uid);
        if (book == null)
            throw new InvalidBookException("Book does not exists");
        else
            bookRepo.delete(book);
    }

    public Book updateBook(BookDto request) throws InvalidBookException, Exception {
        Book book = bookRepo.findByUid(request.getUid());
        if (book == null)
            throw new InvalidBookException("Book does not exists");
        else {
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setDescription(request.getDescription());
            book.setAvailable(request.getAvailable());
            book.setPublishedYear(request.getPublishedYear());
            return bookRepo.save(book);
        }
    }

    public Book updateBookAvailablity(String uid, Boolean available) throws InvalidBookException, Exception {
        Book book = bookRepo.findByUid(uid);
        if (book == null)
            throw new InvalidBookException("Book does not exists");
        else {
            book.setAvailable(available);
            return bookRepo.save(book);
        }
    }

    public void updateBookPath(String uid, String path) throws InvalidBookException, Exception{
        Book book = bookRepo.findByUid(uid);
        if (book == null)
            throw new InvalidBookException("Book does not exists");
        else {
            book.setLocation(path);
            bookRepo.save(book);
        }
    }

    public Book findByUid(String uid) {
        return bookRepo.findByUid(uid);
    }
}
