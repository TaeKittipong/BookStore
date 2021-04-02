package com.kittipong.assignment.common.handler;

import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceApiHttpClientException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerGeneralException(Exception e) {
    log.error("##Error Exception : ", e);
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<?> handlerGeneralException(DisabledException e) {
    log.error("##Error Exception : [{}]", e.getMessage());
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER_DISABLED");
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handlerGeneralException(BadCredentialsException e) {
    log.error("##Error Exception : [{}]", e.getMessage());
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID_CREDENTIALS");
  }

  @ExceptionHandler(BookStoreServiceDataNotFoundException.class)
  public ResponseEntity<?> handlerGeneralException(BookStoreServiceDataNotFoundException e) {
    log.error("##Error Exception : [{}]", e.getMessage());
    return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("BOOKS_DATA_NOT_FOUND");
  }

  @ExceptionHandler(BookStoreServiceApiException.class)
  public ResponseEntity<?> handlerGeneralException(BookStoreServiceApiException e) {
    log.error("##Error Exception : [{}]",e.getMessage());
    return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handlerGeneralException(
          MethodArgumentNotValidException e) {
    String fieldError =
            Optional.ofNullable(e.getBindingResult().getFieldError())
                    .map(FieldError::getField)
                    .orElse("");

    String defaultMessage =
            Optional.ofNullable(e.getBindingResult().getFieldError())
                    .map(FieldError::getDefaultMessage)
                    .orElse("");

    log.error("##Validate Data Exception.[{}] [{}]", fieldError,defaultMessage);
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldError + " " + defaultMessage);
  }

  @ExceptionHandler(BookStoreServiceApiHttpClientException.class)
  public ResponseEntity<?> httpClientException(BookStoreServiceApiHttpClientException e) {

    return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
  }
}
