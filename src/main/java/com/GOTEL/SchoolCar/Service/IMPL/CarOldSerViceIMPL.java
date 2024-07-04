package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.CarOld;
import com.GOTEL.SchoolCar.Mapper.CarOldMapper;
import com.GOTEL.SchoolCar.Service.CarOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CarOldSerViceIMPL implements CarOldService {
    @Autowired
    private CarOldMapper carOldMapper;

    @Override
    public boolean insertCarOld(CarOld carOld) {
        return carOldMapper.insertCarOld(carOld) > 0;
    }

    @Override
    public CarOld selectByUidAndTime(String uid, Timestamp timestamp) {
        return carOldMapper.selectByUidAndTime(uid, timestamp);
    }
}
