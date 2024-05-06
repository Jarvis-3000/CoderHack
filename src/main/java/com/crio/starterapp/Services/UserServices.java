package com.crio.starterapp.Services;

import org.springframework.web.server.ResponseStatusException;

import com.crio.starterapp.DTO.RegisterUserDTO;
import com.crio.starterapp.Models.UserEntity;

import java.util.List;

public interface UserServices {
  // Registers a new user to the contest and save in the repository if not already registered
  public boolean register(RegisterUserDTO registerUserDTO) throws ResponseStatusException;

  // Deregisters a specific user (update registration status to cancelled) from the contest if exist
  public boolean cancelRegistration(String userId) throws ResponseStatusException;

  // Retrieves a list of all registered users
  public List<UserEntity> getAllRegisteredUsers();

  // Retrieves the details of a specific user
  public UserEntity getUserById(String userId) throws ResponseStatusException;

  // Updates the score of a specific user
  public boolean updateUserScore(String userId, int newScore) throws ResponseStatusException;
}
