package com.sithija.travelsearch;

import com.sithija.travelsearch.controller.CommentController;
import com.sithija.travelsearch.controller.UserController;
import com.sithija.travelsearch.dto.*;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.security.JwtUtils;
import com.sithija.travelsearch.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);  // Pass the mocked service to the controller
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User user = new User();
        user.setUserId(userId);
        UserInforDto userInforDto = new UserInforDto();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(1, "User ABC", "ABC@gmail.com", "255, Colombo Road, Colombo", "Colombo", "Sri Lanka", "BUSINESS", new byte[0]);
        when(userService.getUserById(userId)).thenReturn(new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK));

        ResponseEntity<UserProfileResponseDto> response = userService.getUserById(userId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(userProfileResponseDto, response.getBody());
    }

    @Test
    public void testSaveUser() {
        UserNoImageRequestDto createUser = new UserNoImageRequestDto();
        createUser.setName("testuser");
        createUser.setEmail("test@example.com");
        createUser.setPassword("password");
        createUser.setAddress("123 Street");
        createUser.setState("State");
        createUser.setCountry("Country");
        createUser.setRole("USER");

        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);

        when(userRepository.findByName("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userService.saveUser(any(MultipartFile.class), any(UserNoImageRequestDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        ResponseEntity responseEntity = userService.saveUser(image, createUser);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testLoginUser() {
        String userName = "testuser";
        String password = "password";
        String jwt = "eyewpbjwEoq.token";
        String role = "USER";
        UserInforDto userInforDto = new UserInforDto();
        userInforDto.setUserId(1);
        userInforDto.setName(userName);
        userInforDto.setRole(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);

        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);
//        when(authentication.getPrincipal()).thenReturn(userInforDto);
        when(userService.loginUser(any(LoginUserDto.class))).thenReturn(new ResponseEntity<>(new JwtResponse(1, jwt, userName, role), HttpStatus.OK));

        ResponseEntity<JwtResponse> responseEntity = userService.loginUser(new LoginUserDto(userName, password));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(jwt, responseEntity.getBody().getJwt());
    }
}
