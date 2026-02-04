package org.example.entities;

public class Person {
    private final String name;
    private int age;
    public Person(String name, int age) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String cleaned = name.strip(); // strip quita espacios Unicode al inicio/fin
        if (cleaned.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.name = cleaned;
        this.age = age;
    }

    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getBirthYear(int currentYear){
        return currentYear - age;
    }
    public Person haveBirthday(){
        return new Person(name, age +1 );
    }
}
