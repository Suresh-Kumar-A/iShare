package com.gmail.creativegeeksuresh.ishare.repository;

import java.util.List;

import com.gmail.creativegeeksuresh.ishare.model.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    public Book findByUid(String uid);

    public Book findByTitle(String title);

    public List<Book> findByAuthor(String author);

    public List<Book> findByAvailable(Boolean available);
}
