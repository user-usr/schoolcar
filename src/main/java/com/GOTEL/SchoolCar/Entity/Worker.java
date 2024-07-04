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
public class Worker {
    private String WID;
    private String W_RealName;
    private String W_Name;
    private String W_Password;
    private String W_Department;
    private String W_Branch;
    private String W_Tel;
    private String W_Email;
    private String W_JobNumber;
    private String W_ID;
    private Map<String, String> workerTableMap = new HashMap<>(){{
        put("编号", "wid");
        put("用户名", "name");
        put("姓名", "realName");
        put("密码", "password");
        put("部门", "department");
        put("科室", "branch");
        put("电话", "tel");
        put("邮箱", "email");
        put("工号", "jobNumber");
        put("身份证", "id");
    }};
}
