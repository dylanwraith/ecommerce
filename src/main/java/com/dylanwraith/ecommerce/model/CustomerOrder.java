package com.dylanwraith.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerOrderItem> customerOrderItems;

    @Column(name = "purchase_timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant purchaseTimestamp;

    @Column(name = "customer_order_number", nullable = false, unique = true)
    private String customerOrderNumber; // Unique order number
}
