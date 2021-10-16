package com.gmail.creativegeeksuresh.ishare.model;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Column
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String uid;

    @Column(unique = true)
    private String title;

    @Column
    private String author;

    @Column
    private String description;

    @Column
    private String location="";

    @Column
    private Boolean available = Boolean.TRUE;

    @Column(name = "published_year")
    private Year publishedYear;

}
