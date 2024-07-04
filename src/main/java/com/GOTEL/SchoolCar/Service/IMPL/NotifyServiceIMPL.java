package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Notify;
import com.GOTEL.SchoolCar.Mapper.NotifyMapper;
import com.GOTEL.SchoolCar.Service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyServiceIMPL implements NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public List<String> getAllId() {
        return notifyMapper.selectAllId();
    }

    @Override
    public boolean insert(Notify notify) {
        return notifyMapper.insert(notify) > 0;
    }

    @Override
    public List<Notify> getByTid(String tid) {
        return notifyMapper.selectByTid(tid);
    }
}
