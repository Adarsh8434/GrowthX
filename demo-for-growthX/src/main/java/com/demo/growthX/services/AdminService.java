package com.demo.growthX.services;

import com.demo.growthX.entity.Admin;
import com.demo.growthX.entity.Assignment;
import com.demo.growthX.repository.AdminRepository;
import com.demo.growthX.repository.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public void register(Admin admin) {
        adminRepository.save(admin);
    }

    public boolean login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).isPresent();
    }

    public List<Assignment> getAssignments(String email) {
        System.out.println("Fetching assignments for admin: " + email);
        return assignmentRepository.findByAdminEmail(email);
    }

    public void acceptAssignmentById(String assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
        if (assignment != null) {
            assignment.setStatus("Accepted");
            assignmentRepository.save(assignment);
        }
    }

    public void rejectAssignmentById(String assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
        if (assignment != null) {
            assignment.setStatus("Rejected");
            assignmentRepository.save(assignment);
        }
    }   
}
