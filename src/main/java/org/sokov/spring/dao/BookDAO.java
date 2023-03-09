package org.sokov.spring.dao;

import org.sokov.spring.models.Book;
import org.sokov.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks () {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int book_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{book_id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Optional<Book> showBook(String name) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Optional<Person> showPersonWithBook(int book_id) {
        return jdbcTemplate.query("SELECT p.person_id, p.name, p.year_of_birth FROM person p JOIN book b " +
                "ON p.person_id = b.person_id WHERE book_id=?", new Object[]{book_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id=?", book_id);
    }

    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void assignBook(int book_id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", person.getPerson_id(), book_id);
    }

    public void updateBook(Book book, int book_id) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?", book.getName(),
                book.getAuthor(), book.getYear(), book_id);
    }

    public void deleteBook(int book_id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }
}
