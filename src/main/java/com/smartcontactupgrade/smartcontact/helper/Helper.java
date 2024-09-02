package com.smartcontactupgrade.smartcontact.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if(authentication instanceof OAuth2AuthenticationToken){
            System.out.println("google");
            var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var client=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User =(OAuth2User) authentication.getPrincipal();
            String userName = "";
            if(client.equalsIgnoreCase("google")){
                userName=oauth2User.getAttribute("email");

            }else if(client.equalsIgnoreCase("github")){
                userName=oauth2User.getAttribute("email")!=null?oauth2User.getAttribute("email").toString():oauth2User.getAttribute("login").toString()+"@gmail.com";

            }
            return userName;
        }else{
            System.out.println("geting email from local database");
            return authentication.getName();
        }
        
    }
    

    public static String getLinkForEmailVerification(String emailToken){
        String link="http://localhost:8080/auth/verify-email?token="+emailToken;
        return link;
    }
}
