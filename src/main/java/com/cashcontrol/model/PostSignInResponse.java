package com.cashcontrol.model;

import java.util.List;

import com.cashcontrol.dto.TokenPair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSignInResponse {

  private long userId;

  private boolean isUserEnabled;

  private TokenPair authToken;

  private String username;

  private String useremail;




}
