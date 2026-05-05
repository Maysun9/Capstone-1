package com.example.ecommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Rating {
    private String userId;
    private String productId;
    private String merchantId;
    private int rating;

    }

