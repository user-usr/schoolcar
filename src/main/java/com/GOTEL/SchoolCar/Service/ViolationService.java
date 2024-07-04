package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Violation;

import java.util.List;
import java.util.Map;

public interface ViolationService {
    List<Violation> getAllViolation(Map<String, String> pick);

    List<Violation> getViolationByUID(String uid, Map<String, String> pick);
    List<String> getAllVid();
    boolean insertViolation(Violation violation);
}
