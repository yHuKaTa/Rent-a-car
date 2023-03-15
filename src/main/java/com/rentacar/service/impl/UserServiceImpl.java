package com.rentacar.service.impl;

import static com.rentacar.util.VerifyUtil.isUserSaveOk;

import com.rentacar.converter.UserConvertor;
import com.rentacar.dto.UserPasswordUpdate;
import com.rentacar.dto.UserRequest;
import com.rentacar.dto.UserResponse;
import com.rentacar.entity.User;
import com.rentacar.exception.EmailDoublingException;
import com.rentacar.exception.PassportIdDoublingException;
import com.rentacar.exception.PhoneNumberDoublingException;
import com.rentacar.exception.RecordNotFoundException;
import com.rentacar.repository.UserRepository;
import com.rentacar.service.UserService;
import com.rentacar.util.VerifyUtil;
import jakarta.transaction.Transactional;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserConvertor userConvertor;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserConvertor userConvertor) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConvertor = userConvertor;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(
                        String.format("User with id %d not found", id.intValue())));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RecordNotFoundException(
                        String.format("User with email %s not found", email)));
    }

    @Override
    @Transactional
    public UserResponse save(UserRequest userRequest)
            throws EmailDoublingException, PhoneNumberDoublingException, PassportIdDoublingException {
        User newUser = null;
        if (isUserSaveOk(userRepository.findAll(),
                userRequest.getEmail(),
                userRequest.getPhoneNumber(),
                userRequest.getPassportId())) {
            newUser = userRepository.save(userConvertor.toUser(userRequest, bCryptPasswordEncoder));
        }
        return userConvertor.toUserResponse(Objects.requireNonNull(newUser));
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordUpdate userPasswordUpdate) {
        if (VerifyUtil.login(userRepository.findAll(),
                userPasswordUpdate.getEmail(),
                bCryptPasswordEncoder,
                userPasswordUpdate.getPassword())) {
            User modingUser = findByEmail(userPasswordUpdate.getEmail());
            modingUser.setPassword(bCryptPasswordEncoder.encode(
                    userPasswordUpdate.getNewPassword()));
            userRepository.save(modingUser);
        } else {
            throw new RecordNotFoundException("User not found or invalid password");
        }
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            userRepository.deleteById(id);
        }
    }
}
