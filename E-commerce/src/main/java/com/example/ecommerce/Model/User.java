package com.example.ecommerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotNull(message = "Error: User id cannot be empty")
    private String id;

    @NotEmpty(message = "Error: User name can not be empty")
    @Size(min = 5, message = "User name must be more than 5 characters")
    private String username;

    @NotEmpty(message = "Error: Password can not be empty")
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Error: Password must contain letters and numbers")
    private String password;

    @NotEmpty(message = "Error: email can not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Error: role can not be empty")
    @Pattern(regexp = "Admin|Customer",message = "Role must be Admin or Customer")
    private String role;

    @NotNull(message = "Error: balance can not be empty")
    @Positive(message = "Error: balance must be positive")
    private int balance;

 ArrayList<String> buyProduct = new ArrayList<>();

}
