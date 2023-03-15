package com.rentacar.service;

import com.rentacar.dto.ReservationRequest;
import com.rentacar.dto.ReservationResponse;
import com.rentacar.entity.Car;
import com.rentacar.entity.Reservation;
import com.rentacar.entity.User;
import com.rentacar.exception.RecordNotFoundException;
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
