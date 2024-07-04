package com.GOTEL.SchoolCar.Entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
@PropertySource("classpath:mail.yml")
public class Email {
    @Value("${from}")
    private String from;
    private String subject;
    private String text;
    private String to;
}
