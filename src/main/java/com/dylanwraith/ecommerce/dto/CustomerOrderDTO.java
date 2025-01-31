package com.dylanwraith.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerOrderDTO (
        @NotNull Long userId,
        List<CustomerOrderItemDTO> customerOrderItemDTOs
) {}