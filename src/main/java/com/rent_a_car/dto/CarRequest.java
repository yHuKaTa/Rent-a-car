package com.rent_a_car.dto;

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
public class CarRequest {
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}", message = "Brand name must be between 3 and 20 characters")
    private String brand;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,20}", message = "Brand model must be between 3 and 20 characters and digits")
    private String model;

    @NotNull
    @Size(max = 2)
    @Pattern(regexp = "\\d", message = "Seats number must be digit")
    private String seats;

    @NotNull
    @Pattern(regexp = "(\\d+(\\.\\d*)?)", message = "Price for day must be decimal")
    private String dayPrice;
}
