package com.smartcontactupgrade.smartcontact.services;

import com.smartcontactupgrade.smartcontact.forms.MessageForm;

public interface EmailServices {

    void sendEmail(String to,String subject,String body);
    void sendEmailFromDifferent(String to,String subject,String body,String from);
    void sendEmail(MessageForm messageForm);
    void senEmailWithHtml();
    void sendEmailWithAttachment();
    
}
