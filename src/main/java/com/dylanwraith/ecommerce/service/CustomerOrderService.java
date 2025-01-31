package com.dylanwraith.ecommerce.service;

import com.dylanwraith.ecommerce.dto.CustomerOrderDTO;
import com.dylanwraith.ecommerce.dto.CustomerOrderItemDTO;
import com.dylanwraith.ecommerce.model.CustomerOrder;
import com.dylanwraith.ecommerce.model.CustomerOrderItem;
import com.dylanwraith.ecommerce.model.Product;
import com.dylanwraith.ecommerce.model.User;
import com.dylanwraith.ecommerce.repository.CustomerOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;


    private final ProductService productService;

    private final UserService userService;

    public CustomerOrderService(
            CustomerOrderRepository customerOrderRepository,
            ProductService productService,
            UserService userService) {
        this.customerOrderRepository = customerOrderRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public CustomerOrder createCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        User user = userService.getUserById(customerOrderDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + customerOrderDTO.userId()));

        List<Long> productIds
                = customerOrderDTO.customerOrderItemDTOs().stream().map(CustomerOrderItemDTO::productId).toList();

        List<Product> products = productService.getByProductIds(productIds);

        Map<Long, Integer> productIdToQuantity = new HashMap<>();
        for (CustomerOrderItemDTO item : customerOrderDTO.customerOrderItemDTOs()) {
            productIdToQuantity.put(item.productId(), item.quantity());
        }

        CustomerOrder customerOrder = new CustomerOrder();
        List<CustomerOrderItem> customerOrderItems = new ArrayList<>();

        products.forEach(product -> {
            CustomerOrderItem item = new CustomerOrderItem();
            item.setPricePerItemAtPurchaseTime(product.getPrice());
            item.setQuantity(productIdToQuantity.get(product.getId()));
            item.setProduct(product);
            item.setCustomerOrder(customerOrder);
            customerOrderItems.add(item);
        });

        customerOrder.setUser(user);
        customerOrder.setCustomerOrderItems(customerOrderItems);
        customerOrder.setCustomerOrderNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return customerOrderRepository.save(customerOrder);
    }

    public List<CustomerOrder> getCustomerOrdersByUserId(Long userId) {
        return customerOrderRepository.findAllByUserId(userId);
    }
}
