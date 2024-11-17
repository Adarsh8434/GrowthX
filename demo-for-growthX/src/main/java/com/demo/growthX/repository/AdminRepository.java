package com.demo.growthX.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.growthX.entity.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmailAndPassword(String email, String password);
    Optional<Admin> findByEmail(String email);
}
