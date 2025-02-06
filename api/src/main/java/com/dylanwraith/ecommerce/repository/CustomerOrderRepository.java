package com.dylanwraith.ecommerce.repository;

import com.dylanwraith.ecommerce.model.CustomerOrder;
import com.dylanwraith.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findAllByUserId(Long userId);
}
