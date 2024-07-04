package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.EAE;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EAEMapper {
    List<EAE> getAll(@Param("map") Map<String, String> pick);
    List<EAE> getByUID(@Param("uid") String uid, @Param("map") Map<String, String> pick);
    List<String> selectAllEid();
    int insertEAE(EAE eae);
}
