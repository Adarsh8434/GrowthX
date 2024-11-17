package com.demo.growthX.services;



import com.demo.growthX.entity.Admin;
import com.demo.growthX.entity.Assignment;
import com.demo.growthX.entity.User;
import com.demo.growthX.repository.AdminRepository;
import com.demo.growthX.repository.AssignmentRepository;
import com.demo.growthX.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public void register(User user) {
        userRepository.save(user);
    }

    public boolean login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).isPresent();
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void uploadAssignment(String userEmail, String adminEmail, String taskDescription) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Admin admin = adminRepository.findByEmail(adminEmail).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
      
        System.out.println("User found: " + user);
System.out.println("Admin found: " + admin);


        Assignment assignment = new Assignment();
        assignment.setAssignmentId(java.util.UUID.randomUUID().toString());  // Generate unique ID
        assignment.setTaskDescription(taskDescription);
        assignment.setUploadDate(LocalDateTime.now());
        assignment.setUser(user);
        assignment.setAdmin(admin);
        assignment.setStatus("Pending");
    
        try {
            assignmentRepository.save(assignment);
            System.out.println("Assignment saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving assignment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
