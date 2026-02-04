package org.example.valueobjects;

public record PhoneNumber(String value) {
    public PhoneNumber {
        if (value == null) {
            throw new IllegalArgumentException("Phone number cannot be null.");
        }
        value = value.strip();  // reasignas el parámetro "canónico"
        if (value.length() < 9) {
            throw new IllegalArgumentException("Phone number must have at least 9 digits.");
        }
    }
}
