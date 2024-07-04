package com.GOTEL.SchoolCar.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@ToString
@Repository
public class Car {
    private String CID;
    private String UID;
    private String C_PlateNumber;
    private String C_Brand;
    private String C_Model;
    private String C_Type;
    private String C_Color;
    private String U_RealName;
    private String U_StudentID;
}
