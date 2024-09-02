package com.smartcontactupgrade.smartcontact.forms;


import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
   @NotBlank(message = "Username is required")
   @Size(min = 3 ,message = "Min 3 Characters is required ")
   private String name;
   @Email(message = "Invalid Email Address")
   @NotBlank(message = "Email required")
   private String email;
   @NotBlank(message = "Password Required")
   @Size(min = 6,message="Min 6 Characters is required")
   private String password;
   @Size(min = 8, max = 12,message="Invalid phone number ")
   private String phoneNumber;
   @NotBlank(message = "About requires")
   private String about; 
   private MultipartFile profilePic;
}
