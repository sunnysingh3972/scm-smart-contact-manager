package com.smartcontactupgrade.smartcontact.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageForm {
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String message;

}
