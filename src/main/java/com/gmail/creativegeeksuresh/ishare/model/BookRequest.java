package com.gmail.creativegeeksuresh.ishare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books_request")
@Getter
@Setter
@NoArgsConstructor
public class BookRequest {

    @Column
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id", unique = true)
    private String bookId;

    @Column(unique = true)
    private String title;

    @Column(name = "requested_by")
    private String requestedBy;

}
