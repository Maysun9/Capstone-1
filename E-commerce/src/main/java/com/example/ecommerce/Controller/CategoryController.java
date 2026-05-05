package com.example.ecommerce.Controller;

import com.example.ecommerce.Api.ApiResponse;
import com.example.ecommerce.Model.Category;
import com.example.ecommerce.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/amazon-clone/category")
@RequiredArgsConstructor
public class CategoryController {
private final CategoryService categoryService;

//get
    @GetMapping("/get")
    public ResponseEntity<?> getCategory(){
        return ResponseEntity.status(200).body(categoryService.getCategory());
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new ApiResponse("Category added successfully"));
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean update = categoryService.updateCategory(id, category);
        if (update) {
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Category not found"));
    }



    }


