package com.rentacar.service.impl;

import com.rentacar.converter.CarConvertor;
import com.rentacar.dto.CarRequest;
import com.rentacar.dto.CarResponse;
import com.rentacar.dto.CarUpdate;
import com.rentacar.entity.Car;
import com.rentacar.exception.CarNotFoundException;
import com.rentacar.repository.CarRepository;
import com.rentacar.service.CarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarConvertor carConvertor;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarConvertor carConvertor) {

        this.carRepository = carRepository;
        this.carConvertor = carConvertor;
    }

    @Override
    @Transactional
    public CarResponse save(CarRequest carRequest) {
        Car newCar = carRepository.save(carConvertor.toCar(carRequest));
        return carConvertor.toCarResponse(newCar);
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new CarNotFoundException(
                        String.format("Car with id %d not found", id.intValue())));
    }

    @Override
    @Transactional
    public CarResponse updatePrice(CarUpdate carUpdate) {
        Car car = carRepository.findById(Long.parseUnsignedLong(
                carUpdate.getId())).orElseThrow(
                        () -> new CarNotFoundException(
                                String.format("Car with id %s not found", carUpdate.getId())));
        car.setDayPrice(Double.parseDouble(carUpdate.getNewDayPrice()));
        carRepository.save(car);
        return carConvertor.toCarResponse(car);
    }

    @Override
    public void deleteById(Long id) {
        if (carRepository.findById(id).isEmpty()) {
            throw new CarNotFoundException(
                    String.format("Car with id %d not found", id.intValue()));
        } else {
            carRepository.deleteById(id);
        }
    }
}
