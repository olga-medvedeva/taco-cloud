package com.springproject.tacocloud.controller;

import com.springproject.tacocloud.model.*;
import com.springproject.tacocloud.model.Ingredient.Type;
import com.springproject.tacocloud.repository.*;
import com.springproject.tacocloud.model.TacoOrderRequest.Status;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    IngredientRepository ingredientRepo;
    TacoRepository tacoRepo;
    UserRepository userRepo;
    OrderRepository orderRepo;
    OrderRequestRepository orderRequestRepo;

    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository tacoRepo, UserRepository userRepo,
                                OrderRepository orderRepo, OrderRequestRepository orderRequestRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.orderRequestRepo = orderRequestRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel (Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(StreamSupport.stream(ingredients.spliterator(), false).collect(Collectors.toList()), type));
        }
    }

    @ModelAttribute(name = "tacoOrderRequest")
    public TacoOrderRequest order(@AuthenticationPrincipal User user) {
        TacoOrderRequest orderReq = orderRequestRepo.findRequestByUserAndStatus(user, Status.PROCESSED);
        if (orderReq != null) {
            return orderReq;
        }
        orderReq = new TacoOrderRequest();
        orderReq.setUser(user);
        orderRequestRepo.save(orderReq);
        return orderReq;
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, TacoOrderRequest orderReq) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco savedTaco = tacoRepo.save(taco);
        orderReq.addTaco(savedTaco);
        orderRequestRepo.save(orderReq);

        return "redirect:/shoppingCart";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
