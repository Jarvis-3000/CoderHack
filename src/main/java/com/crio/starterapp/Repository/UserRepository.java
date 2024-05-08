package com.crio.starterapp.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.starterapp.Models.UserEntity;
import com.crio.starterapp.enums.RegistrationStatus;

import java.util.List;

public interface UserRepository extends MongoRepository<UserEntity, String> {
  public boolean existsById(String id);

  public boolean existsByUsername(String username);

  public UserEntity findByUsername(String username);

  List<UserEntity> findByRegistrationStatusOrderByScoreDesc(RegistrationStatus registrationStatus);
}
