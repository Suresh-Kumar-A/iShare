package com.gmail.creativegeeksuresh.ishare.dto;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String uid;

    private String title;

    private String author;

    private String description;

    private Boolean available = Boolean.TRUE;

    private Year publishedYear;

    private String location="";

}
