package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.User;
import com.GOTEL.SchoolCar.Mapper.UserMapper;
import com.GOTEL.SchoolCar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser(Map<String, String> pickMap) {
        return userMapper.getAll(pickMap);
    }

    @Override
    public User getUInformationByUID(String uid) {
        return userMapper.getByUID(uid);
    }

    @Override
    public User getUserByUserStudentID(String U_StudentID) {
        return userMapper.getByUserStudentID(U_StudentID);
    }

    @Override
    public Boolean updateUser(User user) {
        if (userMapper.getByUID(user.getUID()) != null) {
            return userMapper.updateUser(user) > 0;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updatePassword(String uid, String password) {
        if (userMapper.getByUID(uid) != null) {
            return userMapper.updatePassword(uid, password) > 0;
        } else {
            return false;
        }
    }

    @Override
    public String getUPasswordByUID(String uid) {
        return userMapper.getPasswordByUID(uid);
    }

    @Override
    public int getUserNumber() {
        return userMapper.getUserNumber();
    }

    @Override
    public List<String> getAllStudentId() {
        return userMapper.getAllStudentId();
    }

    @Override
    public List<String> getAllUID() {
        return userMapper.getAllUID();
    }

    @Override
    public boolean insertByMap(Map<String, String> map) {
        return userMapper.insertByMap(map) > 0;
    }

    @Override
    public boolean insertUsersMaps(List<Map<String, String>> mapList) {
        return userMapper.insertUsersMaps(mapList) > 0;
    }

    @Override
    public String selectEmailByPlate(String plate) {
        return userMapper.selectByPlate(plate).getU_Email();
    }
}
