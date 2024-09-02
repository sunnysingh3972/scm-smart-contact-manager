package com.smartcontactupgrade.smartcontact.services.servicesimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.AppConstraints;
import com.smartcontactupgrade.smartcontact.helper.Helper;
import com.smartcontactupgrade.smartcontact.helper.ResourceNotFoundException;
import com.smartcontactupgrade.smartcontact.repositories.UserRepository;
import com.smartcontactupgrade.smartcontact.services.EmailServices;
import com.smartcontactupgrade.smartcontact.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServices emailServices ;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void deleteUser(String id) {
        // TODO Auto-generated method stub
        User user2 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        userRepository.delete(user2);
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    @Override
    public boolean isUserExist(String userId) {
        // TODO Auto-generated method stub
        User user2 = userRepository.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistbyEmail(String email) {
        // TODO Auto-generated method stub
        User user2 = userRepository.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public User saveUser(User user) {

        // TODO Auto-generated method stub
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstraints.ROLE_USER));
        String emailToken=UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User saveUser= userRepository.save(user);
        String emailLInk=Helper.getLinkForEmailVerification(emailToken);
        emailServices.sendEmail(saveUser.getEmail(), "Verify Acoount :Email Contact Manager ", emailLInk);
        return saveUser;
    }

    @Override
    public Optional<User> updateUser(User user) {
        // TODO Auto-generated method stub
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        User save = userRepository.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public User getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void generateResetToken(String email) {
         User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpirationDate(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
            userRepository.save(user);
            String resetUrl =AppConstraints.BASE_URL + "/reset/reset-password?token=" + token;
            emailServices.sendEmail(email, "Password Reset Request", "To reset your password, click the following link: " + resetUrl);
        }
    }

    @Override
    public User getUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken).orElse(null);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword)); // Consider hashing the password here
        user.setResetToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }

}
