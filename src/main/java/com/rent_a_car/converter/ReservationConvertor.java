package com.rent_a_car.converter;

import com.rent_a_car.dto.CarResponse;
import com.rent_a_car.dto.ReservationRequest;
import com.rent_a_car.dto.ReservationResponse;
import com.rent_a_car.dto.UserResponse;
import com.rent_a_car.entity.Car;
import com.rent_a_car.entity.Reservation;
import com.rent_a_car.entity.User;
import com.rent_a_car.service.impl.CarServiceImpl;
import com.rent_a_car.service.impl.UserServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Component
public class ReservationConvertor {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CarServiceImpl carService;

    public Reservation toReservation(ReservationRequest reservationRequest) {
        User user = userService.findByEmail(reservationRequest.getUserEmail());
        Car car = carService.findById(Long.parseUnsignedLong(reservationRequest.getCarId()));
        LocalDate startingDate = LocalDate.parse(reservationRequest.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse(reservationRequest.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return Reservation.builder()
                .user(user)
                .car(car)
                .startingDate(startingDate)
                .endDate(endDate)
                .totalPrice(ChronoUnit.DAYS.between(startingDate,endDate) * car.getDayPrice())
                .build();
    }

    public ReservationResponse toReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .user(UserResponse.builder()
                        .firstName(reservation.getUser().getFirstName())
                        .lastName(reservation.getUser().getLastName())
                        .email(reservation.getUser().getEmail())
                        .phoneNumber(reservation.getUser().getPhoneNumber())
                        .build())
                .car(CarResponse.builder()
                        .brand(reservation.getCar().getBrand())
                        .model(reservation.getCar().getModel())
                        .dayPrice(String.valueOf(reservation.getCar().getDayPrice()))
                        .build())
                .startDate(reservation.getStartingDate().toString())
                .endDate(reservation.getEndDate().toString())
                .totalPrice(reservation.getTotalPrice())
                .build();

    }
}
