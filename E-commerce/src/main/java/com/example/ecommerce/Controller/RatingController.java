package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/amazon-clone/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getRating() {
        return ResponseEntity.ok(ratingService.getRating());
    }
}