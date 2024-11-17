
package com.demo.growthX.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "assignments")
@Data
@NoArgsConstructor
public class Assignment {
    @Id
    private String assignmentId; 
    private String taskDescription;
    private LocalDateTime uploadDate;
    
   
    @DBRef
    private User user;  // User who uploaded the assignment
    
    @DBRef
    private Admin admin;  // Assigned admin

    private String status;  // Status: "Pending", "Accepted", "Rejected"
}
