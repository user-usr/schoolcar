package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Car;
import com.GOTEL.SchoolCar.Entity.CarNew;
import com.GOTEL.SchoolCar.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarMapper {
    List<Car> getAll(@Param("map")Map<String, String> map);
    Car getByUID(@Param("uid") String uid);
    int updateCar(CarNew carNew);
    List<String> getAllPlateNumber();
    String getCidByPlateNumber(@Param("plate") String plate);
    int insertCar(CarNew carNew);
    int getCarNumber();
    String getUidByCid(@Param("cid")String cid);
}
