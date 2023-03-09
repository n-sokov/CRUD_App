package org.sokov.spring.controllers;

import org.sokov.spring.dao.BookDAO;
import org.sokov.spring.dao.PersonDAO;
import org.sokov.spring.models.Person;
import org.sokov.spring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showPeople(Model model) {
        model.addAttribute("people", personDAO.showPeople());
        return "people/showPeople";
    }

    @GetMapping("/{person_id}")
    public String showPerson(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("person", personDAO.showPerson(person_id));
        model.addAttribute("booksOwner", personDAO.getBooksPersonId(person_id));
        model.addAttribute("books", bookDAO.showBooks());

        return "people/showPerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "/people/newPerson";
        }
        personDAO.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String editPerson(Model model, @PathVariable("person_id") int person_id) {
        model.addAttribute("person", personDAO.showPerson(person_id));
        return "people/editPerson";
    }

    @PatchMapping("/{person_id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("person_id") int person_id) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "/people/editPerson";
        }

        personDAO.updatePerson(person, person_id);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String deletePerson (@PathVariable("person_id") int person_id) {
        personDAO.deletePerson(person_id);
        return "redirect:/people";
    }

}
