package com.demo.growthX.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.growthX.entity.User;

public interface UserRepository extends MongoRepository<User,String>{

Optional<User> findByEmailAndPassword(String email, String password);
Optional<User> findByEmail(String email); 
}
