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
public class Notify {
    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    private String id;
    private String fid;
    private String tid;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp time;
    private String title;
    private String content;
    private String content_type;
}
