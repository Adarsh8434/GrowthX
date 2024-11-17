package com.demo.growthX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.growthX.entity.Admin;
import com.demo.growthX.entity.User;
import com.demo.growthX.services.UserService;

import jakarta.servlet.http.HttpSession;

// import ch.qos.logback.core.model.Model;
import org.springframework.ui.Model;


@Controller
// @RequestMapping("/user")
public class UserController {

      @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String homePage() {
        return "index";  // Serve the index.html page with buttons
    }
     @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";  // Serve register.html page
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";  // Serve login.html page
    }

    @GetMapping("/assignment/upload")
    public String showUploadPage(Model model, HttpSession session) {
        List<Admin> adminList = userService.getAllAdmins();
        System.out.println("Admins: " + adminList); // Debug log
        String userEmail = (String) session.getAttribute("userEmail");
         // Add userEmail and adminList to the model
         System.out.println("Session userEmail in showUploadPage: " + userEmail); // 
    model.addAttribute("userEmail", userEmail);
    model.addAttribute("adminList", adminList);
        return "assignment/upload"; 
    }
   
   @PostMapping("/register")
public String registerAdmin(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    userService.register(user);
    redirectAttributes.addFlashAttribute("message", "User registered successfully. Please login.");
    return "redirect:/user/login";
}

    
    @PostMapping("/login")
public String login(@RequestParam String email, @RequestParam String password, HttpSession session,RedirectAttributes redirectAttributes) {
    boolean success = userService.login(email, password);
    if (success) {
        // Store the user's email in the session or pass it as a redirect attribute
        session.setAttribute("userEmail", email.toLowerCase());
        System.out.println("Session userEmail: " + session.getAttribute("userEmail"));


        redirectAttributes.addFlashAttribute("message", "Login successful.");
        return "redirect:/assignment/upload";  // Redirect to the assignment upload page
    } else {
        redirectAttributes.addFlashAttribute("error", "Login Id and password mismatch");
        return "redirect:/user/login";  // Redirect back to the login page with an error
    }
}


    @PostMapping("/assignment/upload")
public String uploadAssignment(@RequestParam String userEmail, 
                               @RequestParam String adminEmail, 
                               @RequestParam String taskDescription, 
                               RedirectAttributes redirectAttributes) {
    userService.uploadAssignment(userEmail, adminEmail, taskDescription);
    
    // Add success message to display on the upload page
    redirectAttributes.addFlashAttribute("successMessage", "Assignment uploaded successfully");
    
    return "redirect:/assignment/upload"; // Redirect to show the success message
}
     @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = userService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }
 
}
