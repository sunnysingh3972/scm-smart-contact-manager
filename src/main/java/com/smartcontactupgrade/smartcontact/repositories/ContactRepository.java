package com.smartcontactupgrade.smartcontact.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartcontactupgrade.smartcontact.entities.Contact;
import com.smartcontactupgrade.smartcontact.entities.User;
@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {
    //find the contact by user
    List<Contact> findByUser(User user);
    @Query("SELECT c from Contact c WHERE c.user.id=:userid")
    List<Contact> findByUserId(String userid);
    Page<Contact> findByUser(User user,Pageable pagegable);
    Page<Contact> findByUserAndNameContaining(User user,String name,Pageable pagegable);
    Page<Contact> findByUserAndEmailContaining(User user,String email,Pageable pagegable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user,String phoneNumber,Pageable pagegable);
    long countByUserAndFavorite(User user,boolean favorite);
}
