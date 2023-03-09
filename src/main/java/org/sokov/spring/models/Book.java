package org.sokov.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;

    private Integer person_id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 5, max = 100, message = "Название должно быть от 5 до 100 символов")
    private String name;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 5, max = 100, message = "Имя автора должно быть от 5 до 100 символов")
    private String author;

    @Min(value = 1, message = "Год должен быть положительный")
    private int year;

    public Book() {

    }

    public Book(int book_id, Integer person_id, String name, String author, int year) {
        this.book_id = book_id;
        this.person_id = person_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }
}
