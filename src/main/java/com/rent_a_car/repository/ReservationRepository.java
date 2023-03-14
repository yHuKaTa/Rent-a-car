package com.rent_a_car.repository;

import com.rent_a_car.entity.Car;
import com.rent_a_car.entity.Reservation;
import com.rent_a_car.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUser(User user);

    Optional<Reservation> findByCar(Car car);
}
