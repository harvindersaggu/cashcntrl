package com.cashcontrol.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cashcontrol.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest implements Serializable {

  @NotNull
  private String username;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size(min = 8)
  private String password;
  
  private List<Authority> authorities;

}
