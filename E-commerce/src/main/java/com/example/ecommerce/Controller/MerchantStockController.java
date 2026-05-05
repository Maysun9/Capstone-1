package com.example.ecommerce.Controller;

import com.example.ecommerce.Api.ApiResponse;
import com.example.ecommerce.Model.MerchantStock;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.Rating;
import com.example.ecommerce.Service.MerchantStockService;
import com.example.ecommerce.Service.ProductService;
import com.example.ecommerce.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/amazon-clone/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final RatingService ratingService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        int check = merchantStockService.addMerchantStock(merchantStock);
        if (check == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully"));
        }
        if (check == -1) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        if (check == -2) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Error"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStocks(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        boolean isUpdated = merchantStockService.updateMerchantStocks(id, merchantStock);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stocks updated successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stocks with this ID not found!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletMerchantStocks(@PathVariable String id) {
        boolean isDeleted = merchantStockService.deletMerchantStocks(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stocks deleted successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stocks with this ID not found!"));

    }

    @PutMapping("/addStock/{merchantId}/{productId}/{stock}")
    public ResponseEntity<?> addStock(@PathVariable String merchantId, @PathVariable String productId, @PathVariable int stock) {
        boolean isUpdated = merchantStockService.addStock(merchantId, productId, stock);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock increased successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock update failed"));
    }

    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable @Valid String userId, @PathVariable @Valid String productId, @PathVariable @Valid String merchantId) {
        int isDone = merchantStockService.buyProduct(userId, productId, merchantId);
        if (isDone== 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Buy it successfully"));
        }
        if(isDone == 0){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        if(isDone==-1){
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
        if(isDone == -2){
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if(isDone ==-3){
            return ResponseEntity.status(400).body(new ApiResponse("Stock sold out "));
        }
        if(isDone ==-4){
            return ResponseEntity.status(400).body(new ApiResponse("Sorry your balance is low"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Process is failed "));
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<?> getProductsByMerchant(@PathVariable String merchantId) {
        return ResponseEntity.ok(merchantStockService.productByMerchant(merchantId));
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<?> getStock(@PathVariable String productId) {
        int stock = merchantStockService.getStockByProductId(productId);
        if (stock == -1) {
            return ResponseEntity.status(404).body(new ApiResponse("Product not found in stock"));
        }
        return ResponseEntity.ok(stock);
    }
    @PostMapping("/rating/{userId}/{merchantId}/{productId}/{rating}")
    public ResponseEntity<?> rating(@PathVariable String userId, @PathVariable String merchantId, @PathVariable String productId, @PathVariable int rating) {
        int result = merchantStockService.rating(userId, merchantId, productId, rating);
        if (result == 1)
            return ResponseEntity.ok(new ApiResponse("Rating added"));

        if (result == -1)
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid rating (0-5)"));

        if (result == -2)
            return ResponseEntity.badRequest().body(new ApiResponse("User not found"));

        if (result == -3)
            return ResponseEntity.badRequest().body(new ApiResponse("Merchant not found"));

        if (result == -4)
            return ResponseEntity.badRequest().body(new ApiResponse("Product not found"));

        return ResponseEntity.status(404).body(new ApiResponse("Error"));
    }

    @GetMapping("/available/{limit}")
    public ResponseEntity<?> availableProducts(@PathVariable int limit) {
        return ResponseEntity.ok(merchantStockService.availableProducts(limit));
    }
}