package com.rent_a_car.controller;

import com.rent_a_car.converter.UserConvertor;
import com.rent_a_car.dto.UserPasswordUpdate;
import com.rent_a_car.dto.UserRequest;
import com.rent_a_car.dto.UserResponse;
import com.rent_a_car.exception.EmailDoublingException;
import com.rent_a_car.exception.PassportIdDoublingException;
import com.rent_a_car.exception.PhoneNumberDoublingException;
import com.rent_a_car.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserConvertor userConvertor;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userConvertor.toUserResponse(userService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userConvertor.toUserResponse(userService.findByEmail(email)));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest newUser) throws PhoneNumberDoublingException, EmailDoublingException, PassportIdDoublingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(newUser));
    }

    @PutMapping(path = "/change_password")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserPasswordUpdate userPasswordUpdate) {
        userService.updatePassword(userPasswordUpdate);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Password was changed");
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User has been deleted successfully");
    }
}
