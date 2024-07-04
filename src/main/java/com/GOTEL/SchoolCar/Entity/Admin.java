package com.GOTEL.SchoolCar.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@ToString
@Repository
public class Admin {
    private String AID;
    private String A_Password;
    private String A_Tel;
    private String A_Email;
    private String A_JobNumber;
}
