package com.kittipong.assignment.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BookStoreServiceDataNotFoundException extends Exception {
	private final HttpStatus httpStatus;
	public BookStoreServiceDataNotFoundException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}