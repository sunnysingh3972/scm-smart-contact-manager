package com.smartcontactupgrade.smartcontact.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartcontactupgrade.smartcontact.repositories.UserRepository;
@Service
public class SecurityCustomDetailService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //apne user ko load krna h
        return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found Exception: " + username));

    }
    
}
