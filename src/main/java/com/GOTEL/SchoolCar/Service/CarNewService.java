package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.CarNew;

import java.sql.Timestamp;

public interface CarNewService {
    Boolean insertCarNew(CarNew carNew);
    CarNew getByUIDAndTime(String UID, Timestamp timestamp);
    boolean deleteCarNew(String uid);
}