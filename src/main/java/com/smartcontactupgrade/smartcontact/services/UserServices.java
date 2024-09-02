package com.smartcontactupgrade.smartcontact.services;

import java.util.List;
import java.util.Optional;

import com.smartcontactupgrade.smartcontact.entities.User;

public interface UserServices {
  User saveUser(User user);

  Optional<User> getUserById(String id);

  Optional<User> updateUser(User user);

  void deleteUser(String id);

  boolean isUserExist(String userId);

  boolean isUserExistbyEmail(String email);

  List<User> getAllUsers();

  User getUserByEmail(String email);

  void generateResetToken(String email);

  User getUserByResetToken(String resetToken);
  void updatePassword(User user, String newPassword);

}
