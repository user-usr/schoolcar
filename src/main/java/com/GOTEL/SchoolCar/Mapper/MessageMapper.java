package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {
    List<Message> getAll();
    Message getByMID(@Param("MID") String MID);

    int insertMessage(Message message);

    List<Message> getByUID(@Param("UID") String UID);

    List<Message> getByWID(@Param("wid") String wid);

    int doYes(@Param("map") Map<String, String> map);
    int doNo(@Param("map") Map<String, String> map);
}
