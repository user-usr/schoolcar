package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Message;
import com.GOTEL.SchoolCar.Mapper.MessageMapper;
import com.GOTEL.SchoolCar.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceIMPL implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getAllMessage() {
        return messageMapper.getAll();
    }

    @Override
    public Message getMessageByMID(String MID) {
        return messageMapper.getByMID(MID);
    }

    @Override
    public boolean insertMessage(Message message) {
        int i = messageMapper.insertMessage(message);
        return i == 1;
    }

    @Override
    public List<Message> getByUID(String UID) {
        return messageMapper.getByUID(UID);
    }

    @Override
    public List<Message> getByWID(String wid) {
        return messageMapper.getByWID(wid);
    }

    @Override
    public boolean doYes(Map<String, String> map) {
        return messageMapper.doYes(map) > 0;
    }

    @Override
    public boolean doNo(Map<String, String> map) {
        return messageMapper.doNo(map) > 0;
    }


}
