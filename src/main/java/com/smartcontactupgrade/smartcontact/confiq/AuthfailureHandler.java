package com.smartcontactupgrade.smartcontact.confiq;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.smartcontactupgrade.smartcontact.helper.Message;
import com.smartcontactupgrade.smartcontact.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Component
public class AuthfailureHandler implements AuthenticationFailureHandler  {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
       if(exception instanceof DisabledException){
       
        HttpSession session =request.getSession();
        session.setAttribute("message", Message.builder().content("Your account is disabled. Please contact admin.").type(MessageType.red).build());
        response.sendRedirect("/login");
       }else{
        response.sendRedirect("/login?error=true"); 
       }

       
    }
    
}
