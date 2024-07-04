package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> getAll(@Param("map") Map<String, String> pickMap);

    User getByUserStudentID(@Param("U_StudentID") String U_StudentID);

    User getByUID(@Param("UID") String UID);
    int updateUser(User user);

    int updatePassword(@Param("uid") String uid, @Param("password") String password);

    int addUser(User user);

    List<String> getAllUID();

    String getPasswordByUID(@Param("uid") String uid);
    int getUserNumber();
    List<String> getAllStudentId();
    int insertByMap(@Param("map") Map<String, String> map);
    int insertUsersMaps(List<Map<String, String>> mapList);
    User selectByPlate(@Param("plate")String plate);
}
