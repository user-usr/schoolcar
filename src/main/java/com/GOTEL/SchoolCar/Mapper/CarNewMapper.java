package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.CarNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

@Mapper
public interface CarNewMapper {
    CarNew getByUIDAndTime(@Param("UID") String UID, @Param("time")Timestamp timestamp);
    int insertCarNew(CarNew carNew);
    int deleteCarNew(@Param("uid") String uid);
}
