package com.smartcontactupgrade.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontactupgrade.smartcontact.entities.Contact;
import com.smartcontactupgrade.smartcontact.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;
    //get contact of user
@GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        Contact contact = contactService.getById(contactId);
        return contact;
    }
}
