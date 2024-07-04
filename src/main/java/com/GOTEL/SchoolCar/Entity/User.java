package com.GOTEL.SchoolCar.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@Repository
public class User {
    private String UID;
    private String U_RealName;
    private String U_Name;
    private String U_Password;
    private String U_College;
    private String U_Major;
    private String U_Grade;
    private String U_Class;
    private String U_Tel;
    private String U_Email;
    private String U_StudentID;
    private String U_ID;
    private Map<String, String> userTableMap = new HashMap<>(){{
        put("编号", "uid");
        put("用户名", "name");
        put("密码", "password");
        put("姓名", "realName");
        put("学号", "studentID");
        put("学院", "college");
        put("专业", "major");
        put("年级", "grade");
        put("班级", "classNumber");
        put("电话", "tel");
        put("邮箱", "email");
        put("身份证", "id");
    }};
}
