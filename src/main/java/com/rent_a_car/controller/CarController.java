package com.rent_a_car.controller;

import com.rent_a_car.converter.CarConvertor;
import com.rent_a_car.dto.CarRequest;
import com.rent_a_car.dto.CarResponse;
import com.rent_a_car.dto.CarUpdate;
import com.rent_a_car.service.impl.CarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/car")
public class CarController {
    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private CarConvertor carConvertor;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarResponse> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(carConvertor.toCarResponse(carService.findById(id)));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<CarResponse> save(@RequestBody @Valid CarRequest carRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carService.save(carRequest));
    }

    @PutMapping(path = "/change")
    public ResponseEntity<CarResponse> updatePrice(@RequestBody @Valid CarUpdate carUpdate) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(carService.updatePrice(carUpdate));
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Car has been deleted successfully");
    }
}
