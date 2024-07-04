package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Message> getAllMessage();
    Message getMessageByMID(String MID);

    boolean insertMessage(Message message);

    List<Message> getByUID(String UID);

    List<Message> getByWID(String wid);

    boolean doYes(Map<String, String> map);

    boolean doNo(Map<String, String> map);
}
