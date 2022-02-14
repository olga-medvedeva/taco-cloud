package com.springproject.tacocloud.controller;

import com.springproject.tacocloud.model.Taco;
import com.springproject.tacocloud.model.TacoOrderRequest;
import com.springproject.tacocloud.model.User;
import com.springproject.tacocloud.repository.OrderRequestRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
@SessionAttributes("order")
public class ShoppingCartController {

    OrderRequestRepository orderRequestRepo;

    public ShoppingCartController(OrderRequestRepository orderRequestRepo) {
        this.orderRequestRepo = orderRequestRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model, @AuthenticationPrincipal User user) {
        TacoOrderRequest orderReq = orderRequestRepo.findRequestByUserAndStatus(user, TacoOrderRequest.Status.PROCESSED);
        List<Taco> tacos = orderReq.getTacos().stream().toList();
        model.addAttribute("tacos", tacos);
    }

    @GetMapping
    public String shoppingCart() {
        return "shoppingCart";
    }
}
