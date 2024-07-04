package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Violation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ViolationMapper {
    List<Violation> getAll(@Param("map") Map<String, String> pick);
    List<Violation> getByUID(@Param("uid") String uid, @Param("map") Map<String, String> pick);
    List<String> getAllVid();
    int insertViolation(Violation violation);
}
