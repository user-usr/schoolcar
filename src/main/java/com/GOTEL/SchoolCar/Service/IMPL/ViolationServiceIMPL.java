package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Violation;
import com.GOTEL.SchoolCar.Mapper.ViolationMapper;
import com.GOTEL.SchoolCar.Service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ViolationServiceIMPL implements ViolationService {
    @Autowired
    private ViolationMapper violationMapper;

    @Override
    public List<Violation> getAllViolation(Map<String, String> pick) {
        return violationMapper.getAll(pick);
    }

    @Override
    public List<Violation> getViolationByUID(String uid, Map<String, String> pick) {
        return violationMapper.getByUID(uid, pick);
    }

    @Override
    public List<String> getAllVid() {
        return violationMapper.getAllVid();
    }

    @Override
    public boolean insertViolation(Violation violation) {
        return violationMapper.insertViolation(violation) > 0;
    }
}
