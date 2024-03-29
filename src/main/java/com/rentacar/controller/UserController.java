package com.rentacar.controller;

import com.rentacar.converter.UserConvertor;
import com.rentacar.dto.UserPasswordUpdate;
import com.rentacar.dto.UserRequest;
import com.rentacar.dto.UserResponse;
import com.rentacar.exception.EmailDoublingException;
import com.rentacar.exception.PassportIdDoublingException;
import com.rentacar.exception.PhoneNumberDoublingException;
import com.rentacar.service.impl.UserServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private UserServiceImpl userService;

    private UserConvertor userConvertor;

    @Autowired
    public UserController(UserServiceImpl userService, UserConvertor userConvertor) {
        this.userService = userService;
        this.userConvertor = userConvertor;
    }

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
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest newUser)
            throws PhoneNumberDoublingException, EmailDoublingException, PassportIdDoublingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(newUser));
    }

    @PutMapping(path = "/changePassword")
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
