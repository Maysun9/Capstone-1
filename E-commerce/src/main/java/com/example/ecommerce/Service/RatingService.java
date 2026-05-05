package com.example.ecommerce.Service;

import com.example.ecommerce.Model.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private List<Rating> ratings = new ArrayList<>();

public List<Rating> getRating(){
    return ratings;
}
}
