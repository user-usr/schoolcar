package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Worker;
import com.GOTEL.SchoolCar.Mapper.WorkerMapper;
import com.GOTEL.SchoolCar.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkerServiceIMPL implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public List<Worker> getAllWorker(Map<String, String> pickMap) {
        return workerMapper.getAll(pickMap);
    }

    @Override
    public String getWPasswordByWID(String wid) {
        return workerMapper.getPasswordByWID(wid);
    }

    @Override
    public Worker getInfByWID(String wid) {
        return workerMapper.getByWID(wid);
    }

    @Override
    public boolean updateInf(Worker worker) {
        if (workerMapper.getByWID(worker.getWID()) != null) {
            return workerMapper.updateInf(worker) > 0;
        } else {
            return false;
        }

    }

    @Override
    public boolean updatePassword(String wid, String password) {
        if (workerMapper.getByWID(wid) != null) {
            return workerMapper.updatePassword(wid, password) > 0;
        } else {
            return false;
        }
    }

    @Override
    public int getWorkerNumber() {
        return workerMapper.getWorkerNumber();
    }

    @Override
    public Worker getByJobNumber(String jobNumber) {
        return workerMapper.getByJobNumber(jobNumber);
    }

    @Override
    public List<String> getAllWid() {
        return workerMapper.getAllWid();
    }

    @Override
    public boolean insertWorker(Worker worker) {
        return workerMapper.insertWorker(worker) > 0;
    }

    @Override
    public boolean insertByMap(Map<String, String> map) {
        return workerMapper.insertByMap(map) > 0;
    }
}
