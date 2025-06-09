package com.example.TunisiaCars_Back.repository;

import com.example.TunisiaCars_Back.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByCarId(String carId);
    List<Review> findByUserId(String userId);
}

