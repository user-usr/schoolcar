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
public class Message {
    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    private String MID;
    private String UID;
    private String WID;
    private String M_State;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp M_CreateTime;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp M_UpdateTime;
    private String M_Type;
    private String M_Description;
    private String M_Evidence;
    private String U_RealName;
    private String W_RealName;
    private String U_StudentID;
}
