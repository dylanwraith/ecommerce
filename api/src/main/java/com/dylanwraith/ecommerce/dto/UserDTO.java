package com.dylanwraith.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "First name cannot be empty") String firstName,
        @NotBlank(message = "Last name cannot be empty") String lastName
) { }
