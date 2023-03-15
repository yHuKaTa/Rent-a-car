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
public class CarUpdate {
    @NotNull
    @Size(min = 1, max = 3)
    @Pattern(regexp = "\\d", message = "Id must be only with max 3 digit")
    private String id;

    @NotNull
    @Pattern(regexp = "(\\d+(\\.\\d*)?)", message = "Price for day must be decimal")
    private String newDayPrice;
}
