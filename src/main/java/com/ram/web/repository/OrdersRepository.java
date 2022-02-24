package com.ram.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ram.web.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,String> {

	
}
