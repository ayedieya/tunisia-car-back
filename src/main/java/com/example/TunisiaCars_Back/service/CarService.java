package com.example.TunisiaCars_Back.service;


import com.example.TunisiaCars_Back.entity.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getAllCars();

    Optional<Car> getCarById(String id);

    Car createCar(Car car);

    Optional<Car> updateCar(String id, Car updatedCar);

    void deleteCar(String id);

    Optional<Car> toggleAvailability(String id);
}
