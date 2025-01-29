package com.dylanwraith.ecommerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_order_item")
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
    private CustomerOrder customerOrder;

    @OneToOne
    @JoinColumn(name = "product_id") // Foreign key column to 'product' table
    private Product product;
}
