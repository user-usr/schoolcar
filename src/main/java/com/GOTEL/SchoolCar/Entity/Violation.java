package com.GOTEL.SchoolCar.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Repository
public class Violation {
    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    private String VID;
    private String CID;
    private String WID;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp V_Time;
    private String V_Where;
    private String V_Type;
    private String V_Description;
    private String V_Punish;
    private String V_Evidence;
    private String C_PlateNumber;
    private String W_RealName;
}
