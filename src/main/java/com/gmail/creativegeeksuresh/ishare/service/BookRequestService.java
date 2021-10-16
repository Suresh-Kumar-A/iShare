package com.gmail.creativegeeksuresh.ishare.service;

import java.util.ArrayList;
import java.util.List;

import com.gmail.creativegeeksuresh.ishare.dto.BookRequestDto;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidBookException;
import com.gmail.creativegeeksuresh.ishare.model.Book;
import com.gmail.creativegeeksuresh.ishare.model.BookRequest;
import com.gmail.creativegeeksuresh.ishare.repository.BookRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService {

    @Autowired
    private BookRequestRepository bookRequestRepo;

    @Autowired
    private BookService bookService;

    public BookRequest createRequest(BookRequestDto request) throws InvalidBookException, Exception {

        Book book = bookService.findByUid(request.getBookId());
        if (book == null)
            throw new InvalidBookException("Book does not exists");
        else {
            BookRequest newRequest = new BookRequest();
            newRequest.setBookId(request.getBookId());
            newRequest.setTitle(request.getTitle());
            newRequest.setRequestedBy(request.getRequestedBy());
            return bookRequestRepo.save(newRequest);
        }
    }

    public void deleteRequest(String bookId) throws Exception {
        BookRequest request = bookRequestRepo.findByBookId(bookId);
        if (request == null)
            throw new Exception("Requested Book does not exists");
        else {
            bookRequestRepo.delete(request);
        }
    }

    public void updateBookAvailablityAndDeleteRequest(String bookId) throws Exception {
        bookService.updateBookAvailablity(bookId, Boolean.TRUE);
        deleteRequest(bookId);
    }

    public List<BookRequest> getAllBooks() {
        try {
            return (List<BookRequest>) bookRequestRepo.findAll();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }
}
