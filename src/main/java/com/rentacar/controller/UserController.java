package com.rentacar.controller;

import com.rentacar.converter.UserConvertor;
import com.rentacar.dto.UserPasswordUpdate;
import com.rentacar.dto.UserRequest;
import com.rentacar.dto.UserResponse;
import com.rentacar.exception.EmailDoublingException;
import com.rentacar.exception.PassportIdDoublingException;
import com.rentacar.exception.PhoneNumberDoublingException;
import com.rentacar.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserConvertor userConvertor;

    @GetMapping(path = "/{id}")
    @Operation(
            description = "View user with his ID number",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = "User is found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                             "firstName" : "String"
                                                             "lastName" : "String"
                                                             "email" : "String"
                                                             "phoneNumber" : "String"
                                                                    }"""
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User with id (int) not found!"
                                            )

                                    }
                            )
                    )
            })
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userConvertor.toUserResponse(userService.findById(id)));
    }

    @GetMapping
    @Operation(
            description = "View user with his ID number",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = "User is found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                             "firstName" : "String"
                                                             "lastName" : "String"
                                                             "email" : "String"
                                                             "phoneNumber" : "String"
                                                                    }"""
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User with email (string) not found!"
                                            )

                                    }
                            )
                    )
            }
    )
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userConvertor.toUserResponse(userService.findByEmail(email)));
    }

    @PostMapping(path = "/add")
    @Operation(
            description = "Add new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                             "firstName" : "String"
                                                             "lastName" : "String"
                                                             "email" : "String"
                                                             "phoneNumber" : "String"
                                                                    }"""
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "String of exception for doubling data provided by user"
                                            )

                                    }
                            )
                    )
            }
    )
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest newUser)
            throws PhoneNumberDoublingException, EmailDoublingException, PassportIdDoublingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(newUser));
    }

    @PutMapping(path = "/changePassword")
    @Operation(
            description = "View user with his ID number",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "User has change password",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Password was changed"
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User not found or invalid password"
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Password invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Password must contain 8 characters with at least 1 digits,"
                                                            + "lowercase characters and uppercase characters"
                                            )

                                    }
                            )
                    )
            }
    )
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
