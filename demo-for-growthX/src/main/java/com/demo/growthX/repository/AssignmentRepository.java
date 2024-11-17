package com.demo.growthX.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.growthX.entity.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment,String>{
    List<Assignment> findByAdminEmail(String adminEmail);
    List<Assignment> findByUserEmail(String userEmail);
}
