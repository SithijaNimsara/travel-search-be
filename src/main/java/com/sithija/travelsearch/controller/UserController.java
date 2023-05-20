package com.sithija.travelsearch.controller;

import com.sithija.travelsearch.dto.JwtResponse;
import com.sithija.travelsearch.dto.LoginUserDto;
import com.sithija.travelsearch.dto.UserInforDto;
import com.sithija.travelsearch.dto.UserNoImageRequestDto;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.error.HttpExceptionResponse;
import com.sithija.travelsearch.security.JwtUtils;
import com.sithija.travelsearch.service.UserService;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user-infor/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('BUSINESS')")
    @ApiOperation(value = "Find user by it's ID", nickname = "userByIdOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<UserInforDto> userById(
            @ApiParam(value = "Get the user by ID.", required = true) @PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(value = "/create-user", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation(value = "Create user", nickname = "createUserOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    private ResponseEntity<User> createNewUser(
            @ApiParam(value = "User profile picture.") @RequestPart("image") MultipartFile image,
            @ApiParam(value = "User data for create user") @RequestPart("data") UserNoImageRequestDto data) {
        return userService.saveUser(image, data);
    }

    @PostMapping(value = "/login-user")
    @ApiOperation(value = "Login user", nickname = "loginUserOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<?> loginUser (@ApiParam(value = "User credentials") @RequestBody LoginUserDto loginUserDto) {
        return userService.loginUser(loginUserDto);
    }

}