package com.rentacar.converter;

import com.rentacar.dto.CarResponse;
import com.rentacar.dto.ReservationRequest;
import com.rentacar.dto.ReservationResponse;
import com.rentacar.dto.UserResponse;
import com.rentacar.entity.Car;
import com.rentacar.entity.Reservation;
import com.rentacar.entity.User;
import com.rentacar.service.impl.CarServiceImpl;
import com.rentacar.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ReservationConvertor {
    private UserServiceImpl userService;

    private CarServiceImpl carService;

    @Autowired
    public ReservationConvertor(UserServiceImpl userService, CarServiceImpl carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public Reservation toReservation(ReservationRequest reservationRequest) {
        User user = userService.findByEmail(reservationRequest.getUserEmail());
        Car car = carService.findById(Long.parseUnsignedLong(reservationRequest.getCarId()));
        LocalDate startingDate = LocalDate.parse(
                reservationRequest.getStartDate(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse(
                reservationRequest.getEndDate(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return Reservation.builder()
                .user(user)
                .car(car)
                .startingDate(startingDate)
                .endDate(endDate)
                .totalPrice(ChronoUnit.DAYS.between(
                        startingDate,
                        endDate) * car.getDayPrice())
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
