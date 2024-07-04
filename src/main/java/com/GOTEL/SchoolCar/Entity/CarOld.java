package com.GOTEL.SchoolCar.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Repository
public class CarOld {
    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    private String CID;
    private String UID;
    private String C_PlateNumber;
    private String C_Brand;
    private String C_Model;
    private String C_Type;
    private String C_Color;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp C_Time;
}
