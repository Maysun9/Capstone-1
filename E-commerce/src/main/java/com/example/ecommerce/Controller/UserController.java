package com.example.ecommerce.Controller;

import com.example.ecommerce.Api.ApiResponse;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/amazon-clone/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        //ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));
    }

     @GetMapping("/history/{userId}")
    public ResponseEntity<?> history(@PathVariable String userId) {
        List<Product> history = userService.getPurchaseHistory(userId);
        return ResponseEntity.ok(history);
    }




}
