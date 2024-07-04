package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> getAll();
    Admin getByAID(@Param("AID") String AID);
    Admin getByJobNumber(String jobNumber);
    int getAdminNumber();
}
