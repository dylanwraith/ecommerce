package com.dylanwraith.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public record CustomerOrderItemDTO (
        @NotNull Long productId,
        @NotNull Integer quantity
) {}