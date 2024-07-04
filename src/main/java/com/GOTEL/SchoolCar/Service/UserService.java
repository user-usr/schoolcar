package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUser(Map<String, String> pickMap);
    User getUserByUserStudentID(String U_StudentID);
    Boolean updateUser(User user);
    Boolean updatePassword(String uid, String password);
    User getUInformationByUID(String uid);

    String getUPasswordByUID(String uid);
    int getUserNumber();
    List<String> getAllStudentId();
    List<String> getAllUID();
    boolean insertByMap(Map<String, String> map);
    boolean insertUsersMaps(List<Map<String, String>> mapList);
    String selectEmailByPlate(String plate);
}
