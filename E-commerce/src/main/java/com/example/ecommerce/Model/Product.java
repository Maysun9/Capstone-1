package com.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Product {
    @NotNull(message = "Error: Prodect id cannot be empty")
    private String id;

    @NotEmpty(message = "Error: Product name must be more than 3 characters")
    @Size(min=3)
    private String name;

    @NotNull(message = "Error: Product price cannot be empty")
    @Positive(message = "Error: Price must be a positive number")
    private int price;

  @NotNull(message = "Error: Category id cannot be empty")
   private String categoryID;
// for rating
    private int rating=0;
}
