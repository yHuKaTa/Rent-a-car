package com.rentacar.converter;

import com.rentacar.dto.CarRequest;
import com.rentacar.dto.CarResponse;
import com.rentacar.entity.Car;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CarConvertor {
    public Car toCar(CarRequest carRequest) {
        return Car.builder()
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .seats(Integer.parseInt(carRequest.getSeats()))
                .dayPrice(Double.parseDouble(carRequest.getDayPrice()))
                .build();
    }

    public CarResponse toCarResponse(Car car) {
        return CarResponse.builder()
                .brand(car.getBrand())
                .model(car.getModel())
                .dayPrice(String.valueOf(car.getDayPrice()))
                .build();
    }
}
