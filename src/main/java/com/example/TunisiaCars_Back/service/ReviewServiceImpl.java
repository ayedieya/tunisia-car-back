package com.example.TunisiaCars_Back.service;


import com.example.TunisiaCars_Back.entity.Review;
import com.example.TunisiaCars_Back.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(String id, Review updatedReview) {
        return reviewRepository.findById(id).map(review -> {
            updatedReview.setId(id);
            return reviewRepository.save(updatedReview);
        }).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReviewsByCarId(String carId) {
        return reviewRepository.findByCarId(carId);
    }
}

