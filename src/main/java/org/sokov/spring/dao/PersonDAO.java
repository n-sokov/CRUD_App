package org.sokov.spring.dao;

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

    public Optional<Person> showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE user_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void savePerson (Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES(?, ?)", person.getName(), person.getYear_of_birth());
    }

    public void updatePerson (Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE user_id=?", person.getName(),
                person.getYear_of_birth(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE user_id=?", id);
    }

}
