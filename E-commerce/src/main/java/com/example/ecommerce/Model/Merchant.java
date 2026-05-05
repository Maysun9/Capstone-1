package com.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotNull(message = "Merchant id cannot be empty")
    private String id;

    @NotEmpty(message = "Merchant name cannot be empty")
    @Size(min = 3, message = "Merchant name must be more than 3 characters")
    private String name;
}
