package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.EAE;
import com.GOTEL.SchoolCar.Mapper.EAEMapper;
import com.GOTEL.SchoolCar.Service.EAEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EAEServiceIMPL implements EAEService {
    @Autowired
    private EAEMapper eaeMapper;
    @Override
    public List<EAE> getAllEAE(Map<String, String> pick) {
        return eaeMapper.getAll(pick);
    }

    @Override
    public List<EAE> getEAEByUID(String uid, Map<String, String> pick) {
        return eaeMapper.getByUID(uid, pick);
    }

    @Override
    public List<String> getAllEid() {
        return eaeMapper.selectAllEid();
    }

    @Override
    public boolean insertEAE(EAE eae) {
        return eaeMapper.insertEAE(eae) > 0;
    }
}
