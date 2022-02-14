package com.springproject.tacocloud.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationForm {

    @Id
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    private String confirm;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phone);
    }


}
