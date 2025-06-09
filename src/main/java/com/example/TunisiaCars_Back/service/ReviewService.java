package com.example.TunisiaCars_Back.service;



import com.example.TunisiaCars_Back.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews();
    Optional<Review> getReviewById(String id);
    Review createReview(Review review);
    Review updateReview(String id, Review review);
    void deleteReview(String id);
    List<Review> getReviewsByCarId(String carId);
}

