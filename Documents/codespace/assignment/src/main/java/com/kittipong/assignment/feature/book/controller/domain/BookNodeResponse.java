package com.kittipong.assignment.feature.book.controller.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@AllArgsConstructor
public class BookNodeResponse {
    List<BookResponseController> books;

}
