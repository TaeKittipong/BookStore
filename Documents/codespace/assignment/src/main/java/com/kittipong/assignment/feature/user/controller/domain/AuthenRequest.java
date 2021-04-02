package com.kittipong.assignment.feature.user.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
public class AuthenRequest {
	@NotBlank
	private String username;
	@NotBlank private String password;
}