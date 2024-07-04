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
public class News {

    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";
    private String NID;
    private String N_Title;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp N_Time;
    private String N_Author;
    private String N_Content;
}
