package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Admin;
import com.GOTEL.SchoolCar.Mapper.AdminMapper;
import com.GOTEL.SchoolCar.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceIMPL implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.getAll();
    }

    @Override
    public Admin getAdminByAID(String AID) {
        return adminMapper.getByAID(AID);
    }
    @Override
    public int getAdminNumber() {
        return adminMapper.getAdminNumber();
    }
}
