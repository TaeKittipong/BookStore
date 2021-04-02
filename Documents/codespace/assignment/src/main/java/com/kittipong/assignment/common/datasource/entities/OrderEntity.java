package com.kittipong.assignment.common.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@IdClass(OrderPK.class)
public class OrderEntity {
    @Id
    @Column(name = "username", nullable = false)
    private String userName;
    @Id
    @Column(name = "book_id", nullable = false)
    private int bookId;
}
