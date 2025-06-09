package com.example.TunisiaCars_Back.repository;

import com.example.TunisiaCars_Back.entity.Car;
import com.example.TunisiaCars_Back.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findByAvailableTrue();

    List<Car> findByLocation(String location);

    List<Car> findByCategory(Category category);

}
