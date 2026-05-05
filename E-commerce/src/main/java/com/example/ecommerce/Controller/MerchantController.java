package com.example.ecommerce.Controller;

import com.example.ecommerce.Api.ApiResponse;
import com.example.ecommerce.Model.Merchant;
import com.example.ecommerce.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/amazon-clone/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchant() {
        //ArrayList<Merchant> merchants = merchantService.getMerchants();
        return  ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully!"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully!"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));
    }


}
