package com.smartcontactupgrade.smartcontact.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactupgrade.smartcontact.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailToken(String emailToken);
    Optional<User> findByResetToken(String resetToken);

} 
