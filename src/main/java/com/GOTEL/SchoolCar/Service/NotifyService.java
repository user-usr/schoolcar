package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Notify;

import java.util.List;

public interface NotifyService {
    List<String> getAllId();
    boolean insert(Notify notify);
    List<Notify> getByTid(String tid);
}
