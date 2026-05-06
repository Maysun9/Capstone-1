package com.example.ecommerce.Controller;

import com.example.ecommerce.Api.ApiResponse;
import com.example.ecommerce.Model.MerchantStock;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.MerchantStockService;
import com.example.ecommerce.Service.ProductService;
import com.example.ecommerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/amazon-clone/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getProduct() {
        return  ResponseEntity.status(200).body(productService.getProduct());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        int check = productService.addProduct(product);
        if(check==1){
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully!"));
    }
        if(check ==0){
            return ResponseEntity.status(400).body(new ApiResponse("category is not found"));
        }
        if(check == -1){
            return ResponseEntity.status(400).body(new ApiResponse("product is not found"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("process is failed"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("product or category updated successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Product not found or category invalid"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("product deleted successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("product with this ID not found!"));
    }


    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.getByCategory(categoryId));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByProduct(@PathVariable String name){
        ArrayList<Product> products1 = productService.searchByProduct(name);
        if(products1.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("not found"));
        }
        return ResponseEntity.status(200).body(productService.searchByProduct(name));
    }

    @GetMapping("/by-price/{min}/{max}")
    public ResponseEntity<?> byPrice(@PathVariable int min, @PathVariable int max) {
        List<Product> products1 = productService.byPrice(min, max);
        if (products1.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found in this price range"));
        }
        return ResponseEntity.ok(products1);
    }

    @GetMapping("/total-price/{price}")
    public ResponseEntity<?> totalPrice(@PathVariable int price) {
        List<Product> totalPrice = productService.totalPrice(price);
        if (totalPrice.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found under this price"));
        }
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        int usersCount = productService.getUsersCount(userService);
        int productsCount = productService.getProductsCount();
        int totalStock = productService.getTotalStock(merchantStockService);

        return ResponseEntity.ok(new ApiResponse("Users: " + usersCount + ", Products: " + productsCount + ", Total Stock: " + totalStock));
    }


    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<?> countByCategory(@PathVariable String categoryId) {

        int count = productService.countProductsByCategory(categoryId);

        return ResponseEntity.ok(new ApiResponse("Total products in category: " + count));
    }

    @GetMapping("/max-price")
    public ResponseEntity<?> maxPrice() {
        Product product = productService.maxPrice();
        if (product == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.ok(product);
    }
     @GetMapping("/recommend/{productId}")
    public ResponseEntity<?> recommend(@PathVariable String productId) {
        List<Product> recommend = productService.recommendByCategory(productId);
        if (recommend.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No recommendations found"));
        }
        return ResponseEntity.ok(recommend);
    }
}
