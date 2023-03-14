package com.rent_a_car.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationResponse {
    private CarResponse car;
    private UserResponse user;
    private String startDate;
    private String endDate;
    private Double totalPrice;
}
