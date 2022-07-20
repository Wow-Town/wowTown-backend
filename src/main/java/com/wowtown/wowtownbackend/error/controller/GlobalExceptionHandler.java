package com.wowtown.wowtownbackend.error.controller;

import com.wowtown.wowtownbackend.error.common.BadRequest;
import com.wowtown.wowtownbackend.error.common.NotFound;
import com.wowtown.wowtownbackend.error.dto.ErrorResponseDto;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
    List<String> message = new ArrayList<>();
    message.add(e.getMessage());
    BadRequest badRequest = BadRequest.builder().code(400).message(message).build();

    final ErrorResponseDto errorResponse = ErrorResponseDto.builder().error(badRequest).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<?> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    BadRequest badRequest =
        BadRequest.builder()
            .code(400)
            .field(Objects.requireNonNull(e.getFieldError()).getField())
            .message(
                e.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList()))
            .build();
    final ErrorResponseDto errorResponse = ErrorResponseDto.builder().error(badRequest).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
    BadRequest notFound =
        BadRequest.builder()
            .code(400)
            .field(
                e.getConstraintViolations().stream()
                    .map(cv -> cv.getInvalidValue())
                    .collect(Collectors.toList())
                    .get(0)
                    .toString())
            .message(
                e.getConstraintViolations().stream()
                    .map(cv -> cv.getMessage())
                    .collect(Collectors.toList()))
            .build();
    final ErrorResponseDto errorResponse = ErrorResponseDto.builder().error(notFound).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(InstanceNotFoundException.class)
  protected ResponseEntity<?> handleInstanceNotFoundException(InstanceNotFoundException e) {
    NotFound notFound = NotFound.builder().code(404).message(e.getMessage()).build();
    final ErrorResponseDto errorResponse = ErrorResponseDto.builder().error(notFound).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
