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
import io.swagger.v3.oas.annotations.media.Schema;
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
    private UserServiceImpl userService;

    private UserConvertor userConvertor;

    @Autowired
    public UserController(UserServiceImpl userService, UserConvertor userConvertor) {
        this.userService = userService;
        this.userConvertor = userConvertor;
    }

    @GetMapping(path = "/{id}")
    @Operation(
            description = "View user by providing ID number as path variable",
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
                                                             "firstName" : "First Name"
                                                             "lastName" : "Last Name"
                                                             "email" : "user@email.domain"
                                                             "phoneNumber" : "String"
                                                                    }"""
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User with id 0 not found!"
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
            description = "View user by providing his email as link parameter",
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
                                                                "firstName": "First Name",
                                                                "lastName": "Last Name",
                                                                "email": "email@address.domain",
                                                                "phoneNumber": "0000000000"
                                                            }"""
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User with email email@address.domain not found!"
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
                                                                "firstName": "First Name",
                                                                "lastName": "Last Name",
                                                                "email": "email@address.domain",
                                                                "phoneNumber": "0000000000"
                                                            }"""
                                            )

                                    }
                            )
                    ),@ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """ 
                                                    {
                                                        "lastName": "Name must be between 3 and 20 characters",
                                                        "firstName": "size must be between 3 and 20",
                                                        "password": "Password must contain 8 characters with at least 1 digits,lowercase characters and uppercase characters",
                                                        "phoneNumber": "Phone number must be min 5 and max 20 digits",
                                                        "passportId": "size must be between 9 and 11"
                                                    }
                                                    """
                                    )

                            }
                    )
            ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Not Acceptable",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Account with this email exists!"
                                            )

                                    }
                            )
                    )
            }
    )
    public ResponseEntity<UserResponse> addUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserRequest.class)))
            @RequestBody @Valid UserRequest newUser)
            throws PhoneNumberDoublingException, EmailDoublingException, PassportIdDoublingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(newUser));
    }

    @PutMapping(path = "/changePassword")
    @Operation(
            description = "Change user's password",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Accepted",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Password was changed"
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User not found or invalid password"
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                               {
                                                               "newPassword": "Password must contain 8 characters with at least 1 digits,lowercase characters and uppercase characters"
                                                                }"""
                                            )

                                    }
                            )
                    )
            }
    )
    public ResponseEntity<String> updateUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserPasswordUpdate.class)))
            @RequestBody @Valid UserPasswordUpdate userPasswordUpdate) {
        userService.updatePassword(userPasswordUpdate);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Password was changed");
    }

    @Operation(
            description = "Delete user with his ID number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User has been deleted successfully"
                                            )

                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "User not found"
                                            )

                                    }
                            )
                    )
            }
    )
    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User has been deleted successfully");
    }
}
