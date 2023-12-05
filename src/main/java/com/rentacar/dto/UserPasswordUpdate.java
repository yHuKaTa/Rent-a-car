package com.rentacar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class UserPasswordUpdate {
    @NotNull
    @Email(message = "Invalid email!")
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}",
            message = "Password must contain 8 characters with at least 1 digits,"
                    + "lowercase characters and uppercase characters")
    private String newPassword;
}
