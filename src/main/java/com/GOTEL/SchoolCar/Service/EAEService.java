package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.EAE;

import java.util.List;
import java.util.Map;

public interface EAEService {
    List<EAE> getAllEAE(Map<String, String> pick);
    List<EAE> getEAEByUID(String uid, Map<String, String> pick);
    List<String> getAllEid();

    boolean insertEAE(EAE eae);
}
