package com.rent_a_car.converter;

import com.rent_a_car.dto.CarRequest;
import com.rent_a_car.dto.CarResponse;
import com.rent_a_car.entity.Car;
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
