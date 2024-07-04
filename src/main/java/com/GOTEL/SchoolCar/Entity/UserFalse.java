package com.GOTEL.SchoolCar.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Getter
@Setter
@ToString
@Repository
public class UserFalse {
    public UserFalse(Map<String, String> map) {
        this.realName = map.get("realName");
        this.studentID = map.get("studentID");
        this.college = map.get("college");
        this.major = map.get("major");
        this.grade = map.get("grade");
        this.classNumber = map.get("classNumber");
        this.tel = map.get("tel");
        this.email = map.get("email");
        this.id = map.get("id");
        this.reason = map.get("reason");
    }
    private String realName;
    private String studentID;
    private String college;
    private String major;
    private String grade;
    private String classNumber;
    private String tel;
    private String email;
    private String id;
    private String reason;
}
