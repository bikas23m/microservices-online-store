package com.bikas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikas.orderservice.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
