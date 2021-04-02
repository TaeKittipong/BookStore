package com.kittipong.assignment.feature.user.controller.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    @JsonProperty("username")
    @NotBlank private String userName;
    @NotBlank private String password;
    @NotBlank private String name;
    @NotBlank private String surname;
    @JsonProperty("date_of_birth")
    @NotBlank private String dateOfBirth;
}
