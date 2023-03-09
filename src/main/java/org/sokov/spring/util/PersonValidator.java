package org.sokov.spring.util;

import org.sokov.spring.dao.PersonDAO;
import org.sokov.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // посмотреть, есть ли человек с таким же именем в БД
        if (personDAO.showPerson(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "Это имя уже используется");
        }
    }
}
