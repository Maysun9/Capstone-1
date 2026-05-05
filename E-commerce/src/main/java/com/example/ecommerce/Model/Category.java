package com.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotNull(message = "Error: Category id cannot be empty")
    private String id;

    @NotEmpty(message = "Error: Category name cannot be empty")
    @Size(min=3, message = "Category name must be more than 3 characters")
    private  String name;
}
