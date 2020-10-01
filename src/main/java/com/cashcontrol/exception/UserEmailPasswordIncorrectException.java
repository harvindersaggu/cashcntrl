package com.cashcontrol.exception;

public class UserEmailPasswordIncorrectException extends RuntimeException  {

  public UserEmailPasswordIncorrectException(String message) {
    super(message);
  }
}