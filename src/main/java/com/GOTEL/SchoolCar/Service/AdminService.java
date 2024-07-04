package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmin();
    Admin getAdminByAID(String AID);
    int getAdminNumber();
}
