package com.kittipong.assignment.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BookStoreServiceApiHttpClientException extends RuntimeException {

  private final HttpStatus httpStatus;

  public BookStoreServiceApiHttpClientException(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

}
