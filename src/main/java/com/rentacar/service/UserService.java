package com.rentacar.service;

import com.rentacar.dto.UserPasswordUpdate;
import com.rentacar.dto.UserRequest;
import com.rentacar.dto.UserResponse;
import com.rentacar.entity.User;
import com.rentacar.exception.EmailDoublingException;
import com.rentacar.exception.PassportIdDoublingException;
import com.rentacar.exception.PhoneNumberDoublingException;

public interface UserService {
    User findById(Long id);

    User findByEmail(String email);

    UserResponse save(UserRequest userRequest) throws
            EmailDoublingException, PhoneNumberDoublingException, PassportIdDoublingException;

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

    void deleteById(Long id);
}
