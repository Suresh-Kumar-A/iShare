package com.gmail.creativegeeksuresh.ishare.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gmail.creativegeeksuresh.ishare.dto.CustomErrorResponse;
import com.gmail.creativegeeksuresh.ishare.dto.UserDto;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidCredentialsException;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidUserException;
import com.gmail.creativegeeksuresh.ishare.exception.UserAlreadyExistsException;
import com.gmail.creativegeeksuresh.ishare.model.User;
import com.gmail.creativegeeksuresh.ishare.service.UserService;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomJwtService;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalApiController {

    @Autowired
    UserService userService;

    @Autowired
    CustomJwtService customJwtService;

    @Autowired
    CustomUtils customUtils;

    @PostMapping(value = "/create-account")
    public ResponseEntity<?> createUserAccount(@RequestBody UserDto request) {
        try {
            return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException uaex) {
            System.err.println(uaex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.CONFLICT.value(), uaex.getLocalizedMessage(), ""),
                    HttpStatus.CONFLICT);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUserAccount(@RequestBody UserDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> payload = new HashMap<>();
            User loggedInUser = userService.validateUserCredentials(request);

            // pass the user data to be sent in JWT token
            payload.put("refId", customUtils.generateToken());
            payload.put("username", loggedInUser.getUsername());
            payload.put("role", loggedInUser.getRole().getRoleName());

            // send the payload to generate jwt token and send it in response
            response.put("token", customJwtService.generateSignedJwtToken(payload));
            response.put("createdAt", new Date().toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidUserException | InvalidCredentialsException iex) {
            System.err.println(iex.getLocalizedMessage());
            response.put("token", "");
            response.put("errorMessae", iex.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }  catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
