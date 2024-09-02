package com.smartcontactupgrade.smartcontact.confiq;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smartcontactupgrade.smartcontact.entities.Providers;
import com.smartcontactupgrade.smartcontact.entities.User;
import com.smartcontactupgrade.smartcontact.helper.AppConstraints;
import com.smartcontactupgrade.smartcontact.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAutheticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger log = LoggerFactory.getLogger(OAutheticationSuccessHandler.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        var user = (DefaultOAuth2User) authentication.getPrincipal();
        // user.getAttributes().forEach((key, value) -> {
        // log.info("{}: {}", key, value);
        // });
        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstraints.ROLE_USER));
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setPassword("dummy");

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        log.info("authorizedClientRegistrationId=>{}", authorizedClientRegistrationId);
        if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            String email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                    : user.getAttribute("login") + "@gmail.com";

            String picture = user.getAttribute("avatar_url").toString();

            String name = user.getAttribute("login").toString();
            String providerId = user.getName();
            user1.setEmail(email);
            user1.setName(name);
            user1.setProfilePic(picture);
            user1.setProviderUserId(providerId);
            user1.setProvider(Providers.GITHUB);
            user1.setAbout("this is a githubased aauthentication");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();
            user1.setEmail(email);
            user1.setName(name);
            user1.setProfilePic(picture);
            user1.setProviderUserId(user.getName());
            user1.setProvider(Providers.GOOGLE);
            user1.setAbout("this is a google based aauthentication");
        } else {

        }
        User user2 = userRepository.findByEmail(user1.getEmail()).orElse(null);
        if (user2 == null) {
        userRepository.save(user1);
        log.info("user saved");
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
