package com.crio.starterapp.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.crio.starterapp.enums.Badge;
import com.crio.starterapp.enums.RegistrationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
  @Id
  private String id;

  @Indexed
  private String username;

  private int score = 0;

  private RegistrationStatus registrationStatus;

  private Set<Badge> badges;
}
