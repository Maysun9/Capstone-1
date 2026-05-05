package com.example.ecommerce.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotNull(message = "Error: Stock id cannot be empty")
    private String id;
   @NotNull(message = "Error: Product id cannot be empty")
  private String productId;
//
 @NotNull(message = "Error: Merchant id cannot be empty")
  private String merchantId;

    @NotNull(message = "Error: Stock number can not be empty")
    @Min(value = 10, message = "Stock must be at least 10")
    private int stock;
}
