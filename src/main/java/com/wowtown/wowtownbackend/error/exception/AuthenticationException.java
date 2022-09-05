package com.wowtown.wowtownbackend.error.exception;

public class AuthenticationException extends RuntimeException {
  public AuthenticationException() {
    super();
  }

  public AuthenticationException(String s) {
    super(s);
  }

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthenticationException(Throwable cause) {
    super(cause);
  }
}
