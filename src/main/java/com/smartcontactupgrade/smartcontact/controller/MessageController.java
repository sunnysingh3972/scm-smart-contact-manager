package com.smartcontactupgrade.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactupgrade.smartcontact.forms.MessageForm;
import com.smartcontactupgrade.smartcontact.helper.Helper;
import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;
import com.smartcontactupgrade.smartcontact.services.EmailServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MessageController {
    @Autowired
    private EmailServices emailService;

    @GetMapping("/message")
    public String showMessageForm(Model model, Authentication authentication) {
        String email = Helper.getEmailOfLoggedInUser(authentication);

        MessageForm messageForm = new MessageForm();
        messageForm.setFromEmail(email);

        model.addAttribute("messageForm", messageForm);
        return "user/message-form";
    }

    @PostMapping("/send-message")
    public String sendMessage(@ModelAttribute MessageForm messageForm, Model model, HttpSession session) {

        emailService.sendEmail(messageForm);
        session.setAttribute("message",
                Message.builder().content("Your message has been sent successfully!").type(MessageType.green).build());
        // model.addAttribute("successMessage", "Your message has been sent
        // successfully!");
        return "user/message-form";
    }
}
