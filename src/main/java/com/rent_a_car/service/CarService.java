package com.rent_a_car.service;

import com.rent_a_car.dto.CarRequest;
import com.rent_a_car.dto.CarResponse;
import com.rent_a_car.dto.CarUpdate;
import com.rent_a_car.entity.Car;

public interface CarService {
    CarResponse save(CarRequest carRequest);

    Car findById(Long id);

    CarResponse updatePrice(CarUpdate carUpdate);

    void deleteById(Long id);
}
