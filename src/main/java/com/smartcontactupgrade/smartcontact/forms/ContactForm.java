package com.smartcontactupgrade.smartcontact.forms;

import org.springframework.web.multipart.MultipartFile;

import com.smartcontactupgrade.smartcontact.validator.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {  
    @NotBlank(message="name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message="Invalid email address ")
    private String email;
    @NotBlank(message="phone is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message="address is required")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    //anoation jo file validate krnge
    @ValidFile(message = "Inavlid iamge")
    private MultipartFile contactImage;

    private String picture;
}
