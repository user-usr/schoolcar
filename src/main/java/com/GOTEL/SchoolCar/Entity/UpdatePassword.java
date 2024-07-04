package com.GOTEL.SchoolCar.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@ToString
@Getter
@Setter
@Repository
public class UpdatePassword {
    private String oldPassword;
    private String newPassword;
}
