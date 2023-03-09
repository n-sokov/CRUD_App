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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> showPerson(String name) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksPersonId(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void savePerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES(?, ?)", person.getName(), person.getYear_of_birth());
    }

    public void updatePerson(Person person, int person_id) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE person_id=?", person.getName(),
                person.getYear_of_birth(), person_id);
    }

    public void deletePerson(int person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", person_id);
    }

}
