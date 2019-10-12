package com.epam.jpop.libraryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Category category;
    private Long isbn;
    private Publisher publisher;
    private Date publishedDate;
    private Double price;
}
