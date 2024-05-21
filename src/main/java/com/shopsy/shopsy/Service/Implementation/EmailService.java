package com.shopsy.shopsy.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shopsy.shopsy.Dto.EmailModel;
import com.shopsy.shopsy.Dto.ResponseMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResponseMessage responseMessage;

    @Value("${spring.mail.username}")
    private String sender;

    public ResponseEntity<ResponseMessage> sendSimpleMail(EmailModel details) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            responseMessage.setSuccess(true);
            responseMessage.setMessage("Mail Sent Successfully to " + details.getRecipient());
            return ResponseEntity.ok().body(responseMessage);
        }

        catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
}
