package com.demo.growthX.controller;

import com.demo.growthX.entity.Assignment;
import com.demo.growthX.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // User uploads a new assignment
    @PostMapping("/upload")
    public ResponseEntity<String> uploadAssignment(@RequestParam String userEmail, 
                                                   @RequestParam String adminEmail, 
                                                   @RequestBody String taskDescription) {
        assignmentService.uploadAssignment(userEmail, adminEmail, taskDescription);
        return ResponseEntity.ok("Assignment uploaded successfully");
    }

    // User views their submitted assignments
    @GetMapping("/user")
    public ResponseEntity<List<Assignment>> getUserAssignments(@RequestParam String userEmail) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUser(userEmail);
        return ResponseEntity.ok(assignments);
    }

    // Admin views assignments tagged to them
    @GetMapping("/admin")
    public ResponseEntity<List<Assignment>> getAdminAssignments(@RequestParam String adminEmail) {
        List<Assignment> assignments = assignmentService.getAssignmentsByAdmin(adminEmail);
        return ResponseEntity.ok(assignments);
    }

    // Admin accepts an assignment
    @PostMapping("/{assignmentId}/accept")
    public ResponseEntity<String> acceptAssignment(@PathVariable String assignmentId) {
        assignmentService.updateAssignmentStatus(assignmentId, "Accepted");
        return ResponseEntity.ok("Assignment accepted successfully");
    }

    // Admin rejects an assignment
    @PostMapping("/{assignmentId}/reject")
    public ResponseEntity<String> rejectAssignment(@PathVariable String assignmentId) {
        assignmentService.updateAssignmentStatus(assignmentId, "Rejected");
        return ResponseEntity.ok("Assignment rejected successfully");
    }
}
