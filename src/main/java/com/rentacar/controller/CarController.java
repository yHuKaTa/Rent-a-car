package com.rentacar.controller;

import com.rentacar.converter.CarConvertor;
import com.rentacar.dto.CarRequest;
import com.rentacar.dto.CarResponse;
import com.rentacar.dto.CarUpdate;
import com.rentacar.service.impl.CarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/car")
public class CarController {
    private CarServiceImpl carService;

    private CarConvertor carConvertor;

    @Autowired
    public CarController(CarServiceImpl carService, CarConvertor carConvertor) {
        this.carService = carService;
        this.carConvertor = carConvertor;
    }

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
