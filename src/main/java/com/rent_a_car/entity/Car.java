package com.rent_a_car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "number_of_seats")
    private Integer seats;

    @Column(name = "price_for_day")
    private Double dayPrice;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car")
    private Set<Reservation> reservations = new HashSet<>();
}
