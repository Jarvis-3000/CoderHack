package com.crio.starterapp.Services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.crio.starterapp.DTO.RegisterUserDTO;
import com.crio.starterapp.Models.UserEntity;
import com.crio.starterapp.Repository.UserRepository;

public class UserSevicesImplTest1 {
  @MockBean
  private UserRepository userRepository;

  @InjectMocks
  private UserServicesImpl userServices;

  @Test
  public void testRegister_Success() {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO("testUser");
    when(userRepository.existsByUsername("testUser")).thenReturn(false);

    assertTrue(userServices.register(registerUserDTO));

    verify(userRepository, times(1)).save(any(UserEntity.class));
  }
}
