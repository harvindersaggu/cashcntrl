package com.cashcontrol.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenException extends RuntimeException {

  public TokenException(String message) {
    super(message);
  }

}
