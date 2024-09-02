package com.smartcontactupgrade.smartcontact.services.servicesimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartcontactupgrade.smartcontact.entities.Contact;
import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.ResourceNotFoundException;
import com.smartcontactupgrade.smartcontact.repositories.ContactRepository;
import com.smartcontactupgrade.smartcontact.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepo;

    @Override
    public Contact save(Contact contact) {
       String cotactId=UUID.randomUUID().toString();
       contact.setId(cotactId);
       return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
       var oldContact=contactRepo.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("contact not found"));
       oldContact.setName(contact.getName());
       oldContact.setEmail(contact.getEmail());
       oldContact.setPhoneNumber(contact.getPhoneNumber());
       oldContact.setAddress(contact.getAddress());
       oldContact.setDescription(contact.getDescription());
       oldContact.setPicture(contact.getPicture());
       oldContact.setFavorite(contact.isFavorite());
       oldContact.setLinkedInLink(contact.getLinkedInLink());
       oldContact.setWebsiteLink(contact.getWebsiteLink());
       return contactRepo.save(oldContact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with id" + id));
    }

    @Override
    public void delete(String id) {
       var contact=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with id" + id));
       contactRepo.delete(contact);
    }


    @Override
    public List<Contact> getBYUserId(String userId) {
         return contactRepo.findByUserId(userId);
    }

    
    @Override
    public Page<Contact> getByUser(User user, int page, int size,String sortBy,String direction) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size,sort);
      return contactRepo.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction,User user) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);
       return contactRepo.findByUserAndNameContaining(user,name, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String direction,User user) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);
       return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }
 
    @Override
    public Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction,User user) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);
       return contactRepo.findByUserAndEmailContaining(user,email, pageable);
    }

   @Override
   public long getCountByFavorite(User user,boolean favorite) {
         return contactRepo.countByUserAndFavorite(user,favorite);
   }

    

    
    
}
