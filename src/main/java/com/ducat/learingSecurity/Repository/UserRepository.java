package com.ducat.learingSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducat.learingSecurity.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer>  {
    Optional<UserEntity> findByUsername(String username);
}
