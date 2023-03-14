package com.rent_a_car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "passport_id", unique = true)
    private String passportId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "pass")
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();
}
