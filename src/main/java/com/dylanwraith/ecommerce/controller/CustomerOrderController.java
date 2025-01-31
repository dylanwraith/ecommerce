package com.dylanwraith.ecommerce.controller;

import com.dylanwraith.ecommerce.dto.CustomerOrderDTO;
import com.dylanwraith.ecommerce.dto.ProductDTO;
import com.dylanwraith.ecommerce.model.CustomerOrder;
import com.dylanwraith.ecommerce.model.Product;
import com.dylanwraith.ecommerce.service.CustomerOrderService;
import com.dylanwraith.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer-order")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CustomerOrder>> getCustomerOrdersByUserId(@PathVariable Long userId) {
        List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByUserId(userId);
        return ResponseEntity.ok(customerOrders);
    }

    @PostMapping
    public ResponseEntity<CustomerOrder> createCustomerOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        CustomerOrder createdCustomerOrder = customerOrderService.createCustomerOrder(customerOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerOrder);
    }
}
