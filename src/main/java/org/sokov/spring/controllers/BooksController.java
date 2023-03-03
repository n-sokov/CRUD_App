package org.sokov.spring.controllers;

import org.sokov.spring.dao.BookDAO;
import org.sokov.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String showBooks (Model model) {
        model.addAttribute("books", bookDAO.showBooks());
        return "books/showBooks";
    }

    @GetMapping("/{id}")
    public String showBook (@PathVariable  int id, Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/showBook";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/newBook";
    }

    @PostMapping()
    public String createBook (@ModelAttribute("book") Book book) {
        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook (Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook (@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.updateBook(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook (@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

}
