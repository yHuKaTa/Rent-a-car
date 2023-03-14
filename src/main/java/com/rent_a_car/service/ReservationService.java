package com.rent_a_car.service;

import com.rent_a_car.dto.ReservationRequest;
import com.rent_a_car.dto.ReservationResponse;
import com.rent_a_car.entity.Car;
import com.rent_a_car.entity.Reservation;
import com.rent_a_car.entity.User;
import com.rent_a_car.exception.RecordNotFoundException;

import java.util.List;

public interface ReservationService {
    ReservationResponse save(ReservationRequest reservationRequest) throws RecordNotFoundException;

    Reservation findById(Long id);

    List<Reservation> findByUser(User user);

    List<Reservation> findByCar(Car car);

    List<Reservation> findByPeriod(String startingDate, String endDate);

    void deleteById(Long id);

    ReservationResponse changeCar(Long id, Car car);

    ReservationResponse changePeriod(Long id, String newEndDate);
}
