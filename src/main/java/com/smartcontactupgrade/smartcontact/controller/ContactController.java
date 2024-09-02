package com.smartcontactupgrade.smartcontact.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactupgrade.smartcontact.entities.Contact;
import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.forms.ContactForm;
import com.smartcontactupgrade.smartcontact.forms.ContactSearchForm;
import com.smartcontactupgrade.smartcontact.helper.AppConstraints;
import com.smartcontactupgrade.smartcontact.helper.Helper;
import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;
import com.smartcontactupgrade.smartcontact.services.ContactService;
import com.smartcontactupgrade.smartcontact.services.ImageService;
import com.smartcontactupgrade.smartcontact.services.servicesimpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ImageService imageService;

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    // add contact page handler
    @GetMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindigresult,
            Authentication authentication, HttpSession session) {
        // process th form data
        if (bindigresult.hasErrors()) {
            session.setAttribute("message", Message.builder().content("please correct the following error message")
                    .type(MessageType.red).build());
            return "user/add_contact";
        }
        System.out.println("save contact");
        String useName = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServiceImpl.getUserByEmail(useName);

        logger.info("file information:{}", contactForm.getContactImage().getOriginalFilename());

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavorite(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        // image process
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String fileUrl = imageService.uploadImage(contactForm.getContactImage());
            contact.setPicture(fileUrl);

        }
        contactService.save(contact);
        System.out.println(user.getName());
        System.out.println(contact);

        // System.out.println(contactForm.getName());
        session.setAttribute("message",
                Message.builder().content("contact saved successfully").type(MessageType.green).build());

        return "redirect:/user/contacts/add";
    }

    @GetMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstraints.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServiceImpl.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("contacts", pageContact);
        model.addAttribute("pageSize", AppConstraints.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    // serach handler
    @GetMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstraints.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServiceImpl.getUserByEmail(username);
        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), page, size, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), page, size, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), page, size, sortBy,
                    direction, user); //
        }
        logger.info("serachresult:{}", contactSearchForm);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("contacts", pageContact);
        model.addAttribute("pageSize", AppConstraints.PAGE_SIZE);
        return "user/search";
    }

    // delete
    @RequestMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") String id, HttpSession session) {
        contactService.delete(id);
        session.setAttribute("message", Message.builder().content("contact deleted successfully")
                .type(MessageType.green).build());
        return "redirect:/user/contacts";
    }

    // update contact form view
    @GetMapping("/view/{id}")
    public String updateContactView(@PathVariable("id") String id, Model model) {
        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", id);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable("id") String id, @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }
        var contact = contactService.getById(id);
        contact.setId(id);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        // contact.setPicture(contactForm.getPicture());

        // image procee
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            var imageUrl = imageService.uploadImage(contactForm.getContactImage());
            contact.setPicture(imageUrl);
        }

        var updateContact = contactService.update(contact);
        model.addAttribute("message", Message.builder().content("contact updated successfully")
                .type(MessageType.green).build());
        return "redirect:/user/contacts/view/" + id;
    }

}
