package com.rentacar.controller;

import com.rentacar.converter.ReservationConvertor;
import com.rentacar.dto.ReservationCarChange;
import com.rentacar.dto.ReservationPeriodChange;
import com.rentacar.dto.ReservationRequest;
import com.rentacar.dto.ReservationResponse;
import com.rentacar.service.impl.CarServiceImpl;
import com.rentacar.service.impl.ReservationServiceImpl;
import com.rentacar.service.impl.UserServiceImpl;
import com.rentacar.util.VerifyUtil;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationConvertor reservationConvertor;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CarServiceImpl carService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationConvertor.toReservationResponse(reservationService.findById(id)));
    }

    @GetMapping(path = "/getWithUserEmail")
    public ResponseEntity<List<ReservationResponse>> getByUserEmail(@RequestParam String userEmail) {
        List<ReservationResponse> reservationResponses = reservationService
                .findByUser(userService.findByEmail(userEmail))
                .stream()
                .map(reservationConvertor::toReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationResponses);
    }

    @GetMapping(path = "/getWithCarId")
    public ResponseEntity<List<ReservationResponse>> getByCarId(@RequestParam Long carId) {
        List<ReservationResponse> reservationResponses = reservationService
                .findByCar(carService.findById(carId))
                .stream()
                .map(reservationConvertor::toReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationResponses);
    }

    @GetMapping(path = "/getWithPeriod")
    public ResponseEntity<List<ReservationResponse>> getByPeriod(@RequestParam String startingDate,
                                                                 @RequestParam String endDate) {
        if (!(VerifyUtil.isDateFormatOk(startingDate) && (VerifyUtil.isDateFormatOk(endDate)))) {
            throw new RuntimeException("Date format invalid! Provide date format DD/MM/YYYY.");
        } else {
            List<ReservationResponse> reservationResponses = reservationService
                    .findByPeriod(startingDate, endDate)
                    .stream()
                    .map(reservationConvertor::toReservationResponse)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(reservationResponses);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ReservationResponse> addReservation(
            @RequestBody @Valid ReservationRequest reservationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.save(reservationRequest));
    }

    @PutMapping(path = "/{id}/changeCar")
    public ResponseEntity<ReservationResponse> changeCar(@PathVariable Long id,
                                                         @RequestBody @Valid ReservationCarChange carChange) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(reservationService.changeCar(id,
                        carService.findById(Long.parseLong(carChange.getCarId()))));
    }

    @PutMapping(path = "{id}/changePeriod")
    public ResponseEntity<ReservationResponse> changePeriod(@PathVariable Long id,
                                                            @RequestBody @Valid
                                                            ReservationPeriodChange periodChange) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(reservationService.changePeriod(id, periodChange.getNewEndDate()));
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Reservation has been deleted successfully");
    }
}
