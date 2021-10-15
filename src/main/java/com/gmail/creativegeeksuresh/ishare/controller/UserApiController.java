package com.gmail.creativegeeksuresh.ishare.controller;

import com.gmail.creativegeeksuresh.ishare.dto.CustomErrorResponse;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidUserException;
import com.gmail.creativegeeksuresh.ishare.model.User;
import com.gmail.creativegeeksuresh.ishare.service.UserService;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomJwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    @Autowired
    UserService userService;
  
    @Autowired
    CustomJwtService customJwtService;

    
  @GetMapping(value = "/view-all")
  public ResponseEntity<?> getAllUsers() {
    try {
      // To get all users with encrypted password use getAllUsers()
      // To get all users with empty password field use getAllUsersWithoutPassword()
      return new ResponseEntity<>(userService.getAllUsersWithoutPassword(), HttpStatus.OK);
    } catch (InvalidUserException iuex) {
      System.err.println(iuex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.CONFLICT.value(), iuex.getLocalizedMessage(), ""),
          HttpStatus.CONFLICT);
    } catch (Exception ex) {
      System.err.println(ex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/view/{uid}")
  public ResponseEntity<?> getUserByUid(@PathVariable(value = "uid") String uid) {
    try {
      // To get user with encrypted password use findByUid()
      // To get user with empty password field use findByUidWithoutPassword()
      User user = userService.findByUidWithoutPassword(uid);
      if (user != null)
        return new ResponseEntity<>(user, HttpStatus.OK);
      else
        throw new InvalidUserException("User does not exists");
    } catch (InvalidUserException iuex) {
      System.err.println(iuex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.CONFLICT.value(), iuex.getLocalizedMessage(), ""),
          HttpStatus.CONFLICT);
    } catch (Exception ex) {
      System.err.println(ex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
          HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(value = "/update")
  public ResponseEntity<?> updateUser(@RequestBody User user) {
    try {
      return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    } catch (InvalidUserException iuex) {
      System.err.println(iuex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.CONFLICT.value(), iuex.getLocalizedMessage(), ""),
          HttpStatus.CONFLICT);
    } catch (Exception ex) {
      System.err.println(ex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
          HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(value = "/delete/{uid}")
  public ResponseEntity<?> deleteUserByUid(@PathVariable(value = "uid") String uid) {
    try {
      userService.deleteByUid(uid);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (InvalidUserException iuex) {
      System.err.println(iuex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.CONFLICT.value(), iuex.getLocalizedMessage(), ""),
          HttpStatus.CONFLICT);
    } catch (Exception ex) {
      System.err.println(ex.getLocalizedMessage());
      return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
          HttpStatus.BAD_REQUEST);
    }
  }
    
}
