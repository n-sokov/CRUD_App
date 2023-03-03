package org.sokov.spring.models;

public class Person {

    private int id;

    private String name;

    private int year_of_birth;

    public Person() {

    }

    public Person(int id, String name, int year_of_birth) {
        this.id = id;
        this.name = name;
        this.year_of_birth = year_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
