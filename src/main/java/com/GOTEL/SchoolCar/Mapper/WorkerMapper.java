package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkerMapper {
    List<Worker> getAll(@Param("map") Map<String, String> pickMap);
    Worker getByWID(@Param("wid") String wid);
    Worker getByJobNumber(String jobNumber);
    String getPasswordByWID(@Param("wid") String wid);
    int updatePassword(@Param("wid") String wid, @Param("password") String password);
    int updateInf(Worker worker);
    int getWorkerNumber();
    List<String> getAllWid();
    int insertWorker(Worker worker);
    int insertByMap(@Param("map") Map<String, String> map);
}
