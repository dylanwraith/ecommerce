package com.dylanwraith.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_order_item")
@Data
public class CustomerOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price_per_item_at_purchase_time", precision = 10, scale = 2) // 10 digits in total, 2 after decimal
    private BigDecimal pricePerItemAtPurchaseTime;

    @ManyToOne
    @JoinColumn(name = "customer_order_id", nullable = false) // Foreign key column to 'customer_order' table
    @JsonBackReference
    private CustomerOrder customerOrder;

    @OneToOne
    @JoinColumn(name = "product_id") // Foreign key column to 'product' table
    private Product product;
}
