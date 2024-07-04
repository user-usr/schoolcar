package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Email;
import com.GOTEL.SchoolCar.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceIMPL implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public boolean sendEmail(Email email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
