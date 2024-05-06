package com.crio.starterapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.crio.starterapp.DTO.RegisterUserDTO;
import com.crio.starterapp.Models.UserEntity;
import com.crio.starterapp.Services.UserServices;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserServices userServices;

  @PostMapping
  public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
    try {
      userServices.register(registerUserDTO);

      return new ResponseEntity<>("Registration Successfull!", HttpStatus.CREATED);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @GetMapping
  public ResponseEntity<List<UserEntity>> getAllRegisteredUsers() {
    List<UserEntity> allRegisteredUsers = userServices.getAllRegisteredUsers();

    return new ResponseEntity(allRegisteredUsers, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
    try {
      UserEntity user = userServices.getUserById(userId);

      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PutMapping("/{userId}")
  public ResponseEntity<String> updateUserScore(@PathVariable String userId,
      @RequestParam(required = true) int score) {
    try {
      userServices.updateUserScore(userId, score);

      return new ResponseEntity<>("Successfully Updated Score", HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> cancelUserRegistration(@PathVariable String userId) {
    try {
      userServices.cancelRegistration(userId);

      return new ResponseEntity<>("Successfully Cancelled User Registration", HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

}
