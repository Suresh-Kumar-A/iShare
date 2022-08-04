package com.gmail.creativegeeksuresh.ishare.model;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "books")
@Data
@Builder
@AllArgsConstructor
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
    @Default
    private String location="";

    @Column
    @Default
    private Boolean available = Boolean.TRUE;

    @Column(name = "published_year")
    private Year publishedYear;

}
