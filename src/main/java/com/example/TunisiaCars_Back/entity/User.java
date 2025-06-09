package com.example.TunisiaCars_Back.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private String password;


    @Column(unique = true)
    private String email;

    private String phone;
    private String dateOfBirth;
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
