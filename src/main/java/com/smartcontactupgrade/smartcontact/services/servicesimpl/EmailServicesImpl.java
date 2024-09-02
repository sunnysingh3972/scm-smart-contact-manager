package com.smartcontactupgrade.smartcontact.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartcontactupgrade.smartcontact.forms.MessageForm;
import com.smartcontactupgrade.smartcontact.services.EmailServices;
@Service
public class EmailServicesImpl implements EmailServices{
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
       
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("sunnykrsinghcse@gmail.com");
        emailSender.send(message);
    }

    @Override
    public void senEmailWithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'senEmailWithHtml'");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }

    @Override
    public void sendEmail(MessageForm messageForm) {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(messageForm.getToEmail());
        message.setSubject(messageForm.getSubject());
        message.setText(messageForm.getMessage());
        message.setFrom(messageForm.getFromEmail());
        emailSender.send(message);
    }

    @Override
    public void sendEmailFromDifferent(String to, String subject, String body, String from) {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        emailSender.send(message);
    }
    
}
