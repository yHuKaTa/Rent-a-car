package com.rentacar.repository;

import com.rentacar.entity.Car;
import com.rentacar.entity.Reservation;
import com.rentacar.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUser(User user);

    Optional<Reservation> findByCar(Car car);
}
