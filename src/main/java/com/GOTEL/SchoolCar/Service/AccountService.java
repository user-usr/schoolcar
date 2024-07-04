package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.User;
import com.GOTEL.SchoolCar.Result.CommonResult;

public interface AccountService {
    CommonResult<?> login(String loginID, String loginPassword);
    CommonResult<?> logon(User user);
}
