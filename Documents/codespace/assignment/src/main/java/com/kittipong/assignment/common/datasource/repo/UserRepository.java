package com.kittipong.assignment.common.datasource.repo;


import com.kittipong.assignment.common.datasource.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Id> {

    UserEntity findByUserName(String userName);
    void deleteByUserName(String userName);
}
