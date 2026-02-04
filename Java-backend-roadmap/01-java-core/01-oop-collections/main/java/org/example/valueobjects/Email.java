package org.example.valueobjects;

public record Email(String value) {
    public Email{
        if (value == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        value = value.strip();
        if (value.isBlank() || !value.contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
    }
}
