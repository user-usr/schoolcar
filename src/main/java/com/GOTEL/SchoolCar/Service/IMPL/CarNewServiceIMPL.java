package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.CarNew;
import com.GOTEL.SchoolCar.Mapper.CarNewMapper;
import com.GOTEL.SchoolCar.Service.CarNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CarNewServiceIMPL implements CarNewService {
    @Autowired
    CarNewMapper carNewMapper;

    @Override
    public Boolean insertCarNew(CarNew carNew) {
        int i = carNewMapper.insertCarNew(carNew);
        return i != 0;
    }

    @Override
    public CarNew getByUIDAndTime(String UID, Timestamp timestamp) {
        return carNewMapper.getByUIDAndTime(UID, timestamp);
    }

    @Override
    public boolean deleteCarNew(String uid) {
        return carNewMapper.deleteCarNew(uid) > 0;
    }
}
