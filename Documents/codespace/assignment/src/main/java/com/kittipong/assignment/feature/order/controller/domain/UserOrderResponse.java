package com.kittipong.assignment.feature.order.controller.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserOrderResponse {
    private String name;
    private String surname;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private List<Integer> books;
}
