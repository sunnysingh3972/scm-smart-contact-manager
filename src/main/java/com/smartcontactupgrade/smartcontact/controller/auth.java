package com.smartcontactupgrade.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;
import com.smartcontactupgrade.smartcontact.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class auth {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token" )String emailToken,HttpSession session){
       User user= userRepository.findByEmailToken(emailToken).orElse(null);
       System.out.println(user);
      
        if(user != null){
            System.out.println(user.getEmailToken());
            if(user.getEmailToken().equals(emailToken)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepository.save(user);
                session.setAttribute("message", Message.builder().content("Your Email is Verified .Now you can Login ").type(MessageType.green).build());
                return "success_page";
            }
        }
        session.setAttribute("message", Message.builder().content("Something Went Wrong !! ").type(MessageType.red).build());
        return "error_page";
    }
    
}
