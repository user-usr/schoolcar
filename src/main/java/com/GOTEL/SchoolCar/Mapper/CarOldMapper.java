package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.CarOld;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

@Mapper
public interface CarOldMapper {
    int insertCarOld(CarOld carOld);
    CarOld selectByUidAndTime(@Param("uid")String uid, @Param("time")Timestamp timestamp);
}
