package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Notify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyMapper {
    List<String> selectAllId();
    int insert(Notify notify);
    List<Notify> selectByTid(String tid);
}
