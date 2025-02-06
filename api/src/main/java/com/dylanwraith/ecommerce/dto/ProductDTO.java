package com.dylanwraith.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank String name,
        @NotNull @Min(0) BigDecimal price
) {}
