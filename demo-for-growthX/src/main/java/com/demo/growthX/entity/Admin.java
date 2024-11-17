package com.demo.growthX.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection= "admins")
@Data
@NoArgsConstructor
public class Admin {
   
    @Id
    private String email;    
    private String name;
    private String password;
}
