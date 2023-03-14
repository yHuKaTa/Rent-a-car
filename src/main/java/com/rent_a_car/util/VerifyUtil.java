package com.rent_a_car.util;

import com.rent_a_car.entity.Car;
import com.rent_a_car.entity.User;
import com.rent_a_car.exception.EmailDoublingException;
import com.rent_a_car.exception.PassportIdDoublingException;
import com.rent_a_car.exception.PhoneNumberDoublingException;
import com.rent_a_car.exception.RecordNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class VerifyUtil {

    public static boolean login(List<User> users, String email, BCryptPasswordEncoder encoder, String rowPassword) throws RecordNotFoundException {
        if (isEmailUnique(users, email)) {
            return false;
        } else {
            User userForAuthentication = null;
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    userForAuthentication = user;
                    break;
                }
            }
            return encoder.matches(rowPassword, Objects.requireNonNull(userForAuthentication).getPassword());
        }
    }

    public static boolean isUserSaveOk(List<User> users, String email, String phoneNumber, String passportId) throws PassportIdDoublingException, PhoneNumberDoublingException, EmailDoublingException {
        if (isEmailUnique(users, email)) {
            if (isPhoneNumberUnique(users, phoneNumber)) {
                if (isPassportIdUnique(users, passportId)) {
                    return true;
                } else {
                    throw new PassportIdDoublingException("Existing passport id!");
                }
            } else {
                throw new PhoneNumberDoublingException(String.format("Phone number %s is already used!", phoneNumber));
            }
        } else {
            throw new EmailDoublingException(String.format("Email %s is already reserved!", email));
        }
    }

    public static boolean isReservationSaveOk(List<User> users, List<Car> cars, String userEmail, Long carID) {
        boolean isUserOk = false;
        boolean isCarOk = false;
        for (User user : users) {
            if (user.getEmail().equals(userEmail)) {
                isUserOk = true;
                break;
            }
        }
        for (Car car : cars) {
            if (car.getId().equals(carID)) {
                isCarOk = true;
                break;
            }
        }
        return isUserOk && isCarOk;
    }

    public static boolean isEmailUnique(List<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPassportIdUnique(List<User> users, String passportId) {
        for (User user : users) {
            if (user.getPassportId().equals(passportId)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPhoneNumberUnique(List<User> users, String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDateFormatOk(String date) {
        return Pattern.compile("^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
                .matcher(date).matches();
    }
}
