package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Car;
import com.GOTEL.SchoolCar.Entity.CarNew;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Car> getAllCar(Map<String, String> map);
    Car getCarByUID(String UID);
    boolean updateCar(CarNew carNew);
    List<String> getAllPlateNumber();
    String getCidByPlate(String plateNumber);
    boolean insertCar(CarNew carNew);
    int getCarNumber();
    String getUidByCid(String cid);
}
