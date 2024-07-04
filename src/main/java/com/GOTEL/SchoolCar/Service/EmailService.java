package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Email;

import javax.mail.MessagingException;

public interface EmailService {
    boolean sendEmail(Email email) throws MessagingException;
}
