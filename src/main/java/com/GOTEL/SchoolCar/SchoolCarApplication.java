package com.GOTEL.SchoolCar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@ComponentScans({
        @ComponentScan("com.GOTEL.SchoolCar.Utils")
})
@SpringBootApplication
public class  SchoolCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolCarApplication.class, args);
    }

}
