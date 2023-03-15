package com.rentacar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationCarChange {
    @NotNull
    @Size(min = 1, max = 3)
    @Pattern(regexp = "\\d", message = "Car ID must be only with max 3 digit")
    private String carId;
}
