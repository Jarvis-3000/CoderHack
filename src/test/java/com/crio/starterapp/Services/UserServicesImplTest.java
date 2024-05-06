package com.crio.starterapp.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.crio.starterapp.DTO.RegisterUserDTO;
import com.crio.starterapp.Models.UserEntity;
import com.crio.starterapp.Repository.UserRepository;
import com.crio.starterapp.enums.Badge;
import com.crio.starterapp.enums.RegistrationStatus;


public class UserServicesImplTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServicesImpl userServices;

  @BeforeEach
  public void setup() {
    userServices = new UserServicesImpl();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testRegister_Success() {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO("testUser");
    when(userRepository.existsByUsername("testUser")).thenReturn(false);

    assertTrue(userServices.register(registerUserDTO));

    verify(userRepository, times(1)).save(any(UserEntity.class));
  }

  @Test
  public void testRegister_Conflict() {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO("testUser");
    when(userRepository.existsByUsername("testUser")).thenReturn(true);

    assertThrows(ResponseStatusException.class, () -> userServices.register(registerUserDTO));
  }

  @Test
  public void testCancelRegistration_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId("1");
    userEntity.setUsername("testUser");
    userEntity.setRegistrationStatus(RegistrationStatus.REGISTERED);

    when(userRepository.findById("1")).thenReturn(java.util.Optional.of(userEntity));

    assertTrue(userServices.cancelRegistration("1"));
    assertEquals(RegistrationStatus.CANCELLED, userEntity.getRegistrationStatus());
    verify(userRepository, times(1)).save(userEntity);
  }

  @Test
  public void testGetAllRegisteredUsers() {
    List<UserEntity> userList = new ArrayList<>();
    UserEntity user1 = new UserEntity("1", "user1", 50, RegistrationStatus.REGISTERED, new HashSet<>());
    UserEntity user2 = new UserEntity("2", "user2", 30, RegistrationStatus.REGISTERED, new HashSet<>());
    userList.add(user1);
    userList.add(user2);

    when(userRepository.findByRegistrationStatusOrderByScoreDesc(RegistrationStatus.REGISTERED))
        .thenReturn(userList);

    List<UserEntity> result = userServices.getAllRegisteredUsers();

    assertEquals(2, result.size());
    assertEquals("user1", result.get(0).getUsername());
    assertEquals("user2", result.get(1).getUsername());
  }

  @Test
  public void testUpdateUserScore_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId("1");
    userEntity.setUsername("testUser");
    userEntity.setRegistrationStatus(RegistrationStatus.REGISTERED);
    userEntity.setScore(50);
    userEntity.setBadges(new HashSet<>());

    when(userRepository.findById("1")).thenReturn(java.util.Optional.of(userEntity));

    assertTrue(userServices.updateUserScore("1", 70));

    assertEquals(70, userEntity.getScore());
    assertTrue(userEntity.getBadges().contains(Badge.CODE_MASTER));
    verify(userRepository, times(1)).save(userEntity);
  }
}
