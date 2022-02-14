package com.springproject.tacocloud.repository;

import com.springproject.tacocloud.model.TacoOrder;
import com.springproject.tacocloud.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip (String deliveryZip);

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user);

}
