package com.sithija.travelsearch.service;

import com.sithija.travelsearch.dto.*;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.error.HttpExceptionResponse;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<UserProfileResponseDto> getUserById(int id) {
        try {
            User userInfor= userRepository.findById(id).get();
            UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder()

                    .name(userInfor.getName())
                    .email(userInfor.getEmail())
                    .address(userInfor.getAddress())
                    .state(userInfor.getState())
                    .country(userInfor.getCountry())
                    .image(userInfor.getImage())
                    .role(userInfor.getRole())
                    .build();
            return new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity saveUser(MultipartFile image, UserNoImageRequestDto createUser) {
        try {
            if(userRepository.findByName(createUser.getName()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
            if (userRepository.findByEmail(createUser.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            User user = User.builder()
                    .name(createUser.getName())
                    .password(encoder.encode(createUser.getPassword()))
                    .email(createUser.getEmail())
                    .address(createUser.getAddress())
                    .state(createUser.getState())
                    .country(createUser.getCountry())
                    .image(image.getBytes())
                    .role(createUser.getRole())
                    .build();
            User saveUser = userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<JwtResponse> loginUser(LoginUserDto loginUserDto) {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getName(), loginUserDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserInforDto user = (UserInforDto) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(user.getUserId(),
                    jwt,
                    user.getName(),
                    user.getRole());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
//            return ResponseEntity.ok(new JwtResponse(user.getUserId(),
//                    jwt,
//                    user.getName(),
//                    user.getRole()));
        } catch (Exception e) {
//            ErrorDto errorDto = new ErrorDto();
//            errorDto.setStatus(401);
//            errorDto.setMessage("Bad credentials");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
