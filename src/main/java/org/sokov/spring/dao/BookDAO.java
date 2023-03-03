package org.sokov.spring.dao;

import org.sokov.spring.models.Book;
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

    public Optional<Book> showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void updateBook (Book book, int id) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?", book.getName(),
                book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook (int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }
}
