package com.rent_a_car.service;

import com.rent_a_car.dto.UserPasswordUpdate;
import com.rent_a_car.dto.UserRequest;
import com.rent_a_car.dto.UserResponse;
import com.rent_a_car.entity.User;
import com.rent_a_car.exception.EmailDoublingException;
import com.rent_a_car.exception.PassportIdDoublingException;
import com.rent_a_car.exception.PhoneNumberDoublingException;
import com.rent_a_car.exception.RecordNotFoundException;

public interface UserService {
    User findById(Long id);

    User findByEmail(String email);

    UserResponse save(UserRequest userRequest) throws EmailDoublingException, PhoneNumberDoublingException, PassportIdDoublingException;

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

    void deleteById(Long id);
}
