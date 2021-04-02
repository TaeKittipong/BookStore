package com.kittipong.assignment.common.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPK implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userName;
  private int bookId;
}
