package com.gmail.creativegeeksuresh.ishare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String uid;
  private String username;
  private String password;
  private String emailAddress;
  private String displayName;
}
