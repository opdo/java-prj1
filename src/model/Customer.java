package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        verifyEmail(this.email);
    }

    public String toString() {
        return "- First name: " + firstName + " | Last name: " + lastName + " | Email: " + email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static void verifyEmail(String email) {
        // Reference code:
        // https://learn.udacity.com/nanodegrees/nd079/parts/cd0282/lessons/062ec4a8-4840-4ee8-868a-533b57352e3e/concepts/6111d5c8-d4f8-449d-976b-7f5d541ea4d4
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Wrong email
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email wrong format");
        }
    }

    public String getEmail() {
        return email;
    }
}
