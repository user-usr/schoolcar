package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Car;
import com.GOTEL.SchoolCar.Entity.CarNew;
import com.GOTEL.SchoolCar.Mapper.CarMapper;
import com.GOTEL.SchoolCar.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarServiceIMPL implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public List<Car> getAllCar(Map<String, String> map) {
        return carMapper.getAll(map);
    }

    @Override
    public Car getCarByUID(String UID) {
        return carMapper.getByUID(UID);
    }

    @Override
    public boolean updateCar(CarNew carNew) {
        return carMapper.updateCar(carNew) > 0;
    }

    @Override
    public List<String> getAllPlateNumber() {
        return carMapper.getAllPlateNumber();
    }

    @Override
    public String getCidByPlate(String plateNumber) {
        return carMapper.getCidByPlateNumber(plateNumber);
    }

    @Override
    public boolean insertCar(CarNew carNew) {
        return carMapper.insertCar(carNew) > 0;
    }

    @Override
    public int getCarNumber() {
        return carMapper.getCarNumber();
    }

    @Override
    public String getUidByCid(String cid) {
        return carMapper.getUidByCid(cid);
    }


}
