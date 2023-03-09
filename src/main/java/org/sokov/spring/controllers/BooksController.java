package org.sokov.spring.controllers;

import org.sokov.spring.dao.BookDAO;
import org.sokov.spring.dao.PersonDAO;
import org.sokov.spring.models.Book;
import org.sokov.spring.models.Person;
import org.sokov.spring.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String showBooks (Model model) {
        model.addAttribute("books", bookDAO.showBooks());
        return "books/showBooks";
    }

    @GetMapping("/{book_id}")
    public String showBook(@PathVariable("book_id")  int book_id, Model model,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.showBook(book_id));

        Optional<Person> optionalPerson = bookDAO.showPersonWithBook(book_id);

        if (optionalPerson.isPresent()) {
            model.addAttribute("person", optionalPerson.get());
        } else {
            model.addAttribute("people", personDAO.showPeople());
        }

        return "books/showBook";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/newBook";
    }

    @PostMapping()
    public String createBook (@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "books/newBook";
        }

        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String editBook (Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookDAO.showBook(book_id));
        return "books/editBook";
    }

    @PatchMapping("/{book_id}")
    public String updateBook (@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                              @PathVariable("book_id") int book_id) {

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "books/editBook";
        }

        bookDAO.updateBook(book, book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id") int book_id) {
        bookDAO.releaseBook(book_id);
        return "redirect:/books/{book_id}";
    }

    @PatchMapping("/{book_id}/assignBook")
    public String assignBook(@PathVariable("book_id") int book_id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assignBook(book_id, selectedPerson);
        return "redirect:/books/{book_id}";
    }

    @DeleteMapping("/{book_id}")
    public String deleteBook (@PathVariable("book_id") int book_id) {
        bookDAO.deleteBook(book_id);
        return "redirect:/books";
    }

}
