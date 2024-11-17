package com.demo.growthX.controller;

import com.demo.growthX.entity.Admin;
import com.demo.growthX.entity.Assignment;
import com.demo.growthX.services.AdminService;
import com.demo.growthX.services.UserService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
       System.out.println("Login successfully");
        return "admin/login";
    }
    @GetMapping("/register")
    public String register() {
        System.out.println("Registered successfully");
        return "admin/register";
    }
    
 @PostMapping("/register")
public String registerAdmin(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
    adminService.register(admin);
    redirectAttributes.addFlashAttribute("message", "Admin registered successfully. Please login.");
    return "redirect:/admin/login";
}
 @PostMapping("/login")
    public String loginAdmin(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        boolean success = adminService.login(email, password);
        if (success) {
            session.setAttribute("adminEmail", email);
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Login Id and Password Incorrect");
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        String adminEmail = (String) session.getAttribute("adminEmail");

        if (adminEmail == null) {
            // If the session doesn't contain the email, you can redirect to the login page or show an error
            return "redirect:/admin/login";
        }
    
        // Fetch assignments using the admin's email
        List<Assignment> assignments = adminService.getAssignments(adminEmail);
        // Add assignments to the model
        model.addAttribute("assignments", assignments);
        return "admin/dashboard";  // Return the dashboard view
    }
 
    @PostMapping("/assignments/{id}/accept")
    public String acceptAssignment(@PathVariable String id, RedirectAttributes redirectAttributes) {
        adminService.acceptAssignmentById(id);
        redirectAttributes.addFlashAttribute("message", "Assignment accepted successfully.");
        return "redirect:/admin/dashboard";
    }
    
    @PostMapping("/assignments/{id}/reject")
    public String rejectAssignment(@PathVariable String id, RedirectAttributes redirectAttributes) {
        adminService.rejectAssignmentById(id);
        redirectAttributes.addFlashAttribute("message", "Assignment rejected successfully.");
        return "redirect:/admin/dashboard";
    }
}