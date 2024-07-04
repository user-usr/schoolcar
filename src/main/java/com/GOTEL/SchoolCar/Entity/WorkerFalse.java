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
public class WorkerFalse {
    private String realName;
    private String department;
    private String branch;
    private String tel;
    private String email;
    private String jobNumber;
    private String id;
    private String reason;

    public WorkerFalse(Map<String, String> map) {
        this.realName = map.get("realName");
        this.department = map.get("department");
        this.branch = map.get("branch");
        this.tel = map.get("tel");
        this.email = map.get("email");
        this.jobNumber = map.get("jobNumber");
        this.id = map.get("id");
        this.reason = map.get("reason");
    }
}
