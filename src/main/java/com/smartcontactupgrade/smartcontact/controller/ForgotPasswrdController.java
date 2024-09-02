package com.smartcontactupgrade.smartcontact.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.services.UserServices;

@Controller
@RequestMapping("/reset")
public class ForgotPasswrdController {
    @Autowired
    UserServices userService;
    
    @RequestMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        userService.generateResetToken(email);
        model.addAttribute("message", "A password reset link has been sent to your email.");
        return "forgot_password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        User user = userService.getUserByResetToken(token);
        if (user == null || user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token.");
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String token, @RequestParam String password, Model model) {
        User user = userService.getUserByResetToken(token);
        if (user == null || user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset-password";
        }
        userService.updatePassword(user, password);
        return "redirect:/login?resetSuccess";
    }
    
}
