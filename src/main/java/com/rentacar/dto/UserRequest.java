package com.rentacar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}", message = "Name must be between 3 and 20 characters")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}", message = "Name must be between 3 and 20 characters")
    private String lastName;
    @NotNull
    @Size(min = 9, max = 11)
    @Pattern(regexp = "[0123456789]{9,11}", message = "PassportID must be between 9 and 11 digits")
    private String passportId;

    private String address;
    @Size(min = 5, max = 20)
    @Pattern(regexp = "[0123456789]{5,20}", message = "Phone number must be min 5 and max 20 digits")
    private String phoneNumber;

    @NotNull
    @Email(message = "Invalid email!")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}",
            message = "Password must contain 8 characters with at least 1 digits,"
                    + "lowercase characters and uppercase characters")
    private String password;
}
