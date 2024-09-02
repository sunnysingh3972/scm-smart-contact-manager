package com.smartcontactupgrade.smartcontact.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.Helper;
import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;
import com.smartcontactupgrade.smartcontact.services.ContactService;
import com.smartcontactupgrade.smartcontact.services.EmailServices;
import com.smartcontactupgrade.smartcontact.services.servicesimpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private EmailServices emailServices ;

    @GetMapping("/dashboard")
    public String userDashBoard(Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServiceImpl.getUserByEmail(username);
        long count = contactService.getCountByFavorite(user, true);
        model.addAttribute("count", count);
        System.out.println("userdashboard");
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        // var user = (DefaultOAuth2User) authentication.getPrincipal();
        // String email = user.getAttribute("email");
        // String username=Helper.getEmailOfLoggedInUser(authentication);
        // User user = userServiceImpl.getUserByEmail(username);
        // model.addAttribute("loggedInUser", user);
        // log.info("user logged in: {}{}{}",username,user.getName());
        // System.out.println("user profile");

        return "user/profile";
    }

    @GetMapping("/feedback")
    public String userFeedback() {
        return "user/feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("feedback") String feedback,
            HttpSession session,Authentication authentication) {
                // String emailOfUser=Helper.getEmailOfLoggedInUser(authentication);
                // emailServices.sendEmailFromDifferent(emailOfUser, "Feedback ", feedback,email );
                session.setAttribute("message", Message.builder().content("Thank you for your feedback!").type(MessageType.green).build());

                return "user/feedback";
    }

}
