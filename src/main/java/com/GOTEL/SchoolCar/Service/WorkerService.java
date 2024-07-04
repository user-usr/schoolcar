package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Worker;

import java.util.List;
import java.util.Map;

public interface WorkerService {
    List<Worker> getAllWorker(Map<String, String> pickMap);

    String getWPasswordByWID(String wid);

    boolean updatePassword(String wid, String password);

    Worker getInfByWID(String wid);

    boolean updateInf(Worker worker);

    int getWorkerNumber();
    Worker getByJobNumber(String jobNumber);
    List<String> getAllWid();
    boolean insertWorker(Worker worker);
    boolean insertByMap(Map<String, String> map);
}
