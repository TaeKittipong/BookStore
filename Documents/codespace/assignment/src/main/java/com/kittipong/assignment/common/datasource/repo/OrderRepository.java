package com.kittipong.assignment.common.datasource.repo;


import com.kittipong.assignment.common.datasource.entities.OrderEntity;


import com.kittipong.assignment.common.datasource.entities.OrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<OrderEntity, OrderPK> {


    List<OrderEntity> findByUserName(String userName);
    void deleteByUserName(String username);
}
