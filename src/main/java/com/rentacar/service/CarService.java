package com.rentacar.service;

import com.rentacar.dto.CarRequest;
import com.rentacar.dto.CarResponse;
import com.rentacar.dto.CarUpdate;
import com.rentacar.entity.Car;

public interface CarService {
    CarResponse save(CarRequest carRequest);

    Car findById(Long id);

    CarResponse updatePrice(CarUpdate carUpdate);

    void deleteById(Long id);
}
