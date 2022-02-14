package com.springproject.tacocloud.controller;

import com.springproject.tacocloud.model.RegistrationForm;
import com.springproject.tacocloud.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Model model) {
        if (userRepo.findByUsername(form.getUsername()) != null || !form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("notConfirmed", true);
            return "registration";
        }
        userRepo.save(form.toUser(passwordEncoder)).toString();

        return "redirect:/login";
    }
}
