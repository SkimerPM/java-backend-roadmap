package org.example.entities;

import org.example.valueobjects.Email;

import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private Email email;

    public User (String name, Email email){

        if(name == null){
            throw new IllegalArgumentException("Name cannot be null.");
        }
        String cleanedName = name.strip();
        if (cleanedName.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = cleanedName;
        this.email = email;
        this.id = generateId();
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    public void changeEmail(Email newEmail){
        this.email = newEmail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }
}
