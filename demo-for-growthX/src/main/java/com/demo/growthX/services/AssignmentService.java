package com.demo.growthX.services;

import com.demo.growthX.entity.Admin;
import com.demo.growthX.entity.Assignment;
import com.demo.growthX.repository.AdminRepository;
import com.demo.growthX.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AdminRepository adminRepository; 

    public void uploadAssignment(String userEmail, String adminEmail, String taskDescription) {
        
    Admin admin = adminRepository.findById(adminEmail).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        Assignment assignment = new Assignment();
        assignment.setTaskDescription(taskDescription);
        assignment.setUploadDate(LocalDateTime.now());
        assignment.setAssignmentId(java.util.UUID.randomUUID().toString()); 
        // assignment.setUserEmail(userEmail);
        assignment.setAdmin(admin);
        assignment.setStatus("Pending");
        assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsByUser(String userEmail) {
        return assignmentRepository.findByUserEmail(userEmail);
    }

    public List<Assignment> getAssignmentsByAdmin(String adminEmail) {
        return assignmentRepository.findByAdminEmail(adminEmail);
    }

    public void updateAssignmentStatus(String assignmentId, String status) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        assignment.setStatus(status);
        assignmentRepository.save(assignment);
    }
}