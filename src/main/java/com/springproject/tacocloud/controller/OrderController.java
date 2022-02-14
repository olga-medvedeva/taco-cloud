package com.springproject.tacocloud.controller;

import com.springproject.tacocloud.model.TacoOrder;
import com.springproject.tacocloud.model.TacoOrderRequest;
import com.springproject.tacocloud.model.TacoOrderRequest.Status;
import com.springproject.tacocloud.model.User;
import com.springproject.tacocloud.properties.OrderProperties;
import com.springproject.tacocloud.repository.OrderRepository;
import com.springproject.tacocloud.repository.OrderRequestRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;
    private OrderProperties orderProperties;
    private OrderRequestRepository orderRequestRepo;

    public OrderController(OrderProperties orderProperties, OrderRepository orderRepo, OrderRequestRepository orderRequestRepo) {
        this.orderRepo = orderRepo;
        this.orderProperties = orderProperties;
        this.orderRequestRepo = orderRequestRepo;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(@AuthenticationPrincipal User user) {
        TacoOrder order = new TacoOrder();
        order.setUser(user);
        TacoOrderRequest orderReq = orderRequestRepo.findRequestByUserAndStatus(user, Status.PROCESSED);
        order.setPlacedAt(new Date());
        order.setTacoOrderRequest(orderReq);
        return order;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute TacoOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable;
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.yml")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            pageable = PageRequest.of(0, Integer.parseInt(properties.getProperty("pageSize")));
        } catch (IOException e) {
            pageable = PageRequest.of(0, 5);
        }
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }



    @PostMapping
    @Transactional
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        TacoOrderRequest orderReq = orderRequestRepo.findRequestByUserAndStatus(user, Status.PROCESSED);
        orderReq.setStatus(Status.COMPLETE);
        orderRequestRepo.save(orderReq);
        sessionStatus.setComplete();
        return "redirect:/orders";
    }
}
