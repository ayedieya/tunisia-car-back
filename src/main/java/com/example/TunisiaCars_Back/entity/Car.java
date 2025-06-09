package com.example.TunisiaCars_Back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    private String id;

    private String brand;
    private String model;
    private int year;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    private int seats;
    private int doors;
    private boolean airConditioning;
    private double pricePerDay;
    private String location;
    private boolean available;
    private double rating;
    private int reviewCount;

    @ElementCollection
    @CollectionTable(name = "car_images", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "image", length = 2048) // augmente la taille du champ image
    private List<String> images;


    @ElementCollection
    private List<String> features;


    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }

}

