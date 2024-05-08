package com.crio.starterapp.Services;

import java.util.List;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.crio.starterapp.DTO.RegisterUserDTO;
import com.crio.starterapp.Models.UserEntity;
import com.crio.starterapp.Repository.UserRepository;
import com.crio.starterapp.enums.Badge;
import com.crio.starterapp.enums.RegistrationStatus;

@Service
public class UserServicesImpl implements UserServices {
  @Autowired
  private UserRepository userRepository;

  @Override
  public boolean register(RegisterUserDTO registerUserDTO) throws ResponseStatusException {
    String username = registerUserDTO.getUsername();
    UserEntity user = userRepository.findByUsername(username);

    // if user exists
    if (user != null) {
      if (user.getRegistrationStatus() == RegistrationStatus.REGISTERED) {
        throw new ResponseStatusException(HttpStatus.CONFLICT);
      } else {
        user.setRegistrationStatus(RegistrationStatus.REGISTERED);

        // save the user with updated data
        userRepository.save(user);
      }

    } else {
      UserEntity newUser = new UserEntity();
      newUser.setUsername(username);
      newUser.setScore(0);
      newUser.setRegistrationStatus(RegistrationStatus.REGISTERED);
      newUser.setBadges(new HashSet<>());

      // save the user with new data
      userRepository.save(newUser);
    }

    return true;
  }

  @Override
  public boolean cancelRegistration(String userId) throws ResponseStatusException {
    UserEntity registeredUser = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    registeredUser.setRegistrationStatus(RegistrationStatus.CANCELLED);
    registeredUser.setBadges(new HashSet<>());
    registeredUser.setScore(0);

    // save the user with updates data
    userRepository.save(registeredUser);

    return true;
  }

  // return all registered user order by score in descending order
  @Override
  public List<UserEntity> getAllRegisteredUsers() {
    return userRepository.findByRegistrationStatusOrderByScoreDesc(RegistrationStatus.REGISTERED);
  }

  @Override
  public UserEntity getUserById(String userId) throws ResponseStatusException {
    UserEntity registeredUser = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (registeredUser.getRegistrationStatus() == RegistrationStatus.CANCELLED) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return registeredUser;
  }

  @Override
  public boolean updateUserScore(String userId, int newScore) throws ResponseStatusException {
    UserEntity registeredUser = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (registeredUser.getRegistrationStatus() == RegistrationStatus.CANCELLED ||
        newScore > 100 ||
        newScore < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    registeredUser.setScore(newScore);
    addBadge(registeredUser, newScore);

    // save the user with updates data
    userRepository.save(registeredUser);

    return true;
  }

  private void addBadge(UserEntity user, int score) {
    // add badge according to score
    if (score >= 1 && score < 30) {
      user.getBadges().add(Badge.CODE_NINJA);
    } else if (score >= 30 && score < 60) {
      user.getBadges().add(Badge.CODE_CHAMP);
    } else {
      // >= 60 && <= 100
      user.getBadges().add(Badge.CODE_MASTER);
    }
  }

}
