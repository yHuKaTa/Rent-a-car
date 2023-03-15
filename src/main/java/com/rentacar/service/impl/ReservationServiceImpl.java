package com.rentacar.service.impl;


import com.rentacar.converter.ReservationConvertor;
import com.rentacar.dto.ReservationRequest;
import com.rentacar.dto.ReservationResponse;
import com.rentacar.entity.Car;
import com.rentacar.entity.Reservation;
import com.rentacar.entity.User;
import com.rentacar.exception.ReservationNotFoundException;
import com.rentacar.repository.CarRepository;
import com.rentacar.repository.ReservationRepository;
import com.rentacar.repository.UserRepository;
import com.rentacar.service.ReservationService;
import com.rentacar.util.VerifyUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationConvertor reservationConvertor;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  CarRepository carRepository,
                                  ReservationConvertor reservationConvertor) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.reservationConvertor = reservationConvertor;
    }

    @Override
    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        Reservation newReservation = null;
        if (VerifyUtil.isReservationSaveOk(
                userRepository.findAll(),
                carRepository.findAll(),
                reservationRequest.getUserEmail(),
                Long.parseUnsignedLong(reservationRequest.getCarId()))) {
            newReservation = reservationRepository.save(
                    reservationConvertor.toReservation(reservationRequest));
        }
        return reservationConvertor.toReservationResponse(
                Objects.requireNonNull(newReservation));
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new ReservationNotFoundException(
                        String.format("Reservation with id %d not found", id.intValue())));
    }

    @Override
    public List<Reservation> findByUser(User user) {
        List<Reservation> reservations = new ArrayList<>(reservationRepository.findAll());
        reservations.removeIf(reservation -> !reservation.getUser().equals(user));
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException(
                    String.format("Reservations with user email %s not found", user.getEmail()));
        } else {
            return reservations;
        }
    }

    @Override
    public List<Reservation> findByCar(Car car) {
        List<Reservation> reservations = new ArrayList<>(reservationRepository.findAll());
        reservations.removeIf(reservation -> !reservation.getCar().equals(car));
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException(
                    String.format("Reservations with car ID %d not found", car.getId().intValue()));
        } else {
            return reservations;
        }
    }

    @Override
    public List<Reservation> findByPeriod(String startingDate, String endDate) {
        LocalDate startDate;
        LocalDate endingDate;
        if (VerifyUtil.isDateFormatOk(startingDate) && VerifyUtil.isDateFormatOk(endDate)) {
            startDate = LocalDate.parse(
                    startingDate,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            endingDate = LocalDate.parse(
                    endDate,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            throw new RuntimeException("Date format invalid! Provide date format DD/MM/YYYY.");
        }
        if (endingDate.isBefore(startDate)) {
            throw new RuntimeException("End date must be after start date of reservation!");
        }
        List<Reservation> searchedReservations = new ArrayList<>(reservationRepository.findAll());

        for (int i = 0; i < searchedReservations.size(); i++) {
            if (startDate.isAfter(searchedReservations.get(i).getStartingDate())) {
                searchedReservations.remove(searchedReservations.remove(i));
                i--;
            } else if (endingDate.isBefore(searchedReservations.get(i).getEndDate())) {
                searchedReservations.remove(searchedReservations.remove(i));
                i--;
            }
        }

        if (searchedReservations.isEmpty()) {
            throw new RuntimeException(
                    String.format("Reservations with period from %s to %s not found", startingDate, endDate));
        } else {
            return searchedReservations;
        }
    }


    @Override
    public void deleteById(Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            throw new ReservationNotFoundException(
                    String.format("Reservation with id %d not found", id.intValue()));
        } else {
            reservationRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public ReservationResponse changeCar(Long id, Car car) {
        Reservation reservation = findById(id);
        reservation.setCar(car);
        reservationRepository.save(reservation);
        return reservationConvertor.toReservationResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse changePeriod(Long id, String newEndDate) {
        Reservation reservation;
        LocalDate endDate;
        if (VerifyUtil.isDateFormatOk(newEndDate)) {
            endDate = LocalDate.parse(
                    newEndDate,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            reservation = findById(id);
            if (endDate.isBefore(reservation.getStartingDate())
                    || endDate.isBefore(reservation.getEndDate())) {
                throw new RuntimeException("Date must be greater than start and end date of reservation!");
            } else {
            reservation.setEndDate(endDate);
            reservationRepository.save(reservation);
            }
        } else {
            throw new RuntimeException("Date format invalid! Provide date format DD/MM/YYYY.");
        }
        return reservationConvertor.toReservationResponse(reservation);
    }
}
