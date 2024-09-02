package com.smartcontactupgrade.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.Helper;
import com.smartcontactupgrade.smartcontact.services.servicesimpl.UserServiceImpl;

@ControllerAdvice
public class RootController {
    @Autowired
    UserServiceImpl userServiceImpl;
  
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if(authentication==null)return;
        System.out.println("Add logged user information into model " );
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServiceImpl.getUserByEmail(username);
        model.addAttribute("loggedInUser", user);    
    }
}
