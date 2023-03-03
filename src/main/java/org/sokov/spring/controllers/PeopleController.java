package org.sokov.spring.controllers;

import org.sokov.spring.dao.PersonDAO;
import org.sokov.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showPeople(Model model) {
        model.addAttribute("people", personDAO.showPeople());
        return "people/showPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPerson(id));
        return "people/showPerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.showPerson(id));
        return "people/editPerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.updatePerson(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson (@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

}
