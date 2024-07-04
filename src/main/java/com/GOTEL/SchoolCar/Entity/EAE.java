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
public class EAE {
    private static final String DDFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    private String EID;
    private String CID;
    @JsonFormat(pattern = DDFormat, timezone = TIME_ZONE)
    private Timestamp E_Time;
    private String E_Direction;
    private String E_Where;
    private String C_PlateNumber;
}
