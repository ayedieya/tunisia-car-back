package com.example.TunisiaCars_Back.service;


import com.example.TunisiaCars_Back.entity.Car;
import com.example.TunisiaCars_Back.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    @Autowired
    private  CarRepository carRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarById(String id) {
        return carRepository.findById(id);
    }

    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> updateCar(String id, Car updatedCar) {
        return carRepository.findById(id).map(car -> {
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setLocation(updatedCar.getLocation());
            car.setPricePerDay(updatedCar.getPricePerDay());
            car.setAvailable(updatedCar.isAvailable());
            car.setImages(updatedCar.getImages());
            return carRepository.save(car);
        });
    }

    @Override
    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }

    @Override
    public Optional<Car> toggleAvailability(String id) {
        return carRepository.findById(id).map(car -> {
            car.setAvailable(!car.isAvailable());
            return carRepository.save(car);
        });
    }


}
