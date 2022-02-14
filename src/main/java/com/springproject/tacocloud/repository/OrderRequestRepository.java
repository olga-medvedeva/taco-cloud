package com.springproject.tacocloud.repository;

import com.springproject.tacocloud.model.TacoOrderRequest;
import com.springproject.tacocloud.model.TacoOrderRequest.Status;
import com.springproject.tacocloud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRequestRepository extends JpaRepository<TacoOrderRequest, Long> {

    TacoOrderRequest findRequestByUserAndStatus(User user, Status status);
}
