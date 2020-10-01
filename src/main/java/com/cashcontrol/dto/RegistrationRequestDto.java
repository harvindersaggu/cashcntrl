package com.cashcontrol.dto;

import java.util.List;

import com.cashcontrol.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

  private String username;

  private String email;

  private String password;
  
  private List<Authority> authorities;

}
