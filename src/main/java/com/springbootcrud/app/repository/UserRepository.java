package com.springbootcrud.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootcrud.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
