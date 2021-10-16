package com.gmail.creativegeeksuresh.ishare.repository;

import com.gmail.creativegeeksuresh.ishare.model.BookRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRequestRepository extends CrudRepository<BookRequest, Integer> {

    public BookRequest findByBookId(String bookId);
}