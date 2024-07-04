package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.CarOld;

import java.sql.Timestamp;

public interface CarOldService {
    boolean insertCarOld(CarOld carOld);
    public CarOld selectByUidAndTime(String uid, Timestamp timestamp);
}
