package com.smartcontactupgrade.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.forms.UserForm;
import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;
import com.smartcontactupgrade.smartcontact.services.ImageService;
import com.smartcontactupgrade.smartcontact.services.servicesimpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("home");
        // sending to view
        model.addAttribute("name", "Smart Contact manger");
        model.addAttribute("Youtubechannel", "http://www.youtube.com");
        model.addAttribute("github", "http://github.com");
        return "home";
    }

    // about
    @GetMapping("/about")
    public String aboutPage() {
        System.out.println("about");
        return "about";
    }

    // services
    @GetMapping("/services")
    public String services() {
        System.out.println("services");
        return "services";
    }

    // contact
    @GetMapping("/contact")
    public String contact() {
        System.out.println("contact");
        return "contact";
    }
    @PostMapping("/contact")
    public String savefeedback(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("subject") String subject, @RequestParam("message") String message,HttpSession session){
        System.out.println(name+" "+email+" "+subject+" "+message);
        session.setAttribute("message", Message.builder().content("Thanks for giving your valuable time").type(MessageType.green).build());
        System.out.println("contact");
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        System.out.println("register");
        return "register";
    }

    @PostMapping("/do_register")
    public String processRegister(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult rBindingResult,
            HttpSession session,Model model){
        System.out.println(userForm);
        if (rBindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "register";
        }
        User user = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .phoneNumber(userForm.getPhoneNumber())
        .enabled(false) // Set enabled to false
        .build();
         // image procee
         if (userForm.getProfilePic() != null &&!userForm.getProfilePic().isEmpty()) {
            var imageUrl = imageService.uploadImage(userForm.getProfilePic());
            user.setProfilePic(imageUrl);
        }
        User save = userServiceImpl.saveUser(user);
        
        System.out.println("saved successfully");
        System.out.println(save);
        Message message = Message.builder().content("Registration Successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/register";
    }
}
