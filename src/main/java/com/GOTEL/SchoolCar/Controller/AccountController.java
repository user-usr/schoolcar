package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.*;
import com.GOTEL.SchoolCar.Entity.Structure.PieDataItem;
import com.GOTEL.SchoolCar.Entity.Structure.StructureBar;
import com.GOTEL.SchoolCar.Entity.Structure.StructurePie;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountServiceIMPL accountServiceIMPL;
    @Autowired
    UserServiceIMPL userServiceIMPL;
    @Autowired
    WorkerServiceIMPL workerServiceIMPL;
    @Autowired
    CarServiceIMPL carServiceIMPL;
    @Autowired
    ViolationServiceIMPL violationServiceIMPL;
    @Autowired
    AdminServiceIMPL adminServiceIMPL;

    @GetMapping("login")
    public CommonResult<?> login(HttpServletRequest request){
        String loginID = request.getParameter("loginID");
        String loginPassword = request.getParameter("loginPassword");

        return accountServiceIMPL.login(loginID, loginPassword);
    }

    @PostMapping("logon")
    public CommonResult<?> logon(@RequestBody User user) {
        return accountServiceIMPL.logon(user);
    }

    @GetMapping("user-structure")
    public CommonResult<?> getUserStructure() {
        int userNumber = userServiceIMPL.getUserNumber();
        int workerNumber = workerServiceIMPL.getWorkerNumber();
        int adminNumber = adminServiceIMPL.getAdminNumber();

        StructurePie structurePie = new StructurePie();
        structurePie.setTitle("用户情况");
        structurePie.setStructure(new ArrayList<>(){{
            add(new PieDataItem("普通用户", userNumber));
            add(new PieDataItem("工作人员", workerNumber));
            add(new PieDataItem("管理员", adminNumber));
        }});
        structurePie.setSumNum(userNumber + workerNumber + adminNumber);

        return CommonResult.success(structurePie);
    }

    @GetMapping("user-car-structure")
    public CommonResult<?> getUserCarStructure() {
        int carNumber = carServiceIMPL.getCarNumber();
        int userNumber = userServiceIMPL.getUserNumber();

        StructurePie structurePie = new StructurePie();
        structurePie.setTitle("电动车持有情况");
        structurePie.setStructure(new ArrayList<>(){{
            add(new PieDataItem("持有电动车", carNumber));
            add(new PieDataItem("未持有电动车", userNumber - carNumber));
        }});
        structurePie.setSumNum(carNumber);

        return CommonResult.success(structurePie);
    }

    @GetMapping("car-violation-structure")
    public CommonResult<?> getCarViolationStructure() {
        List<Violation> violations = violationServiceIMPL.getAllViolation(new HashMap<>());
        int carNumber = carServiceIMPL.getCarNumber();
        List<String> plateNumbers = new ArrayList<>();
        for (Violation violation : violations) {
            if (!plateNumbers.contains(violation.getC_PlateNumber())) {
                plateNumbers.add(violation.getC_PlateNumber());
            }
        }

        StructurePie structurePie = new StructurePie();
        structurePie.setTitle("电动车违规情况");
        structurePie.setStructure(new ArrayList<>(){{
            add(new PieDataItem("存在违规的电动车数量", plateNumbers.size()));
            add(new PieDataItem("不存在违规的电动车数量", carNumber - plateNumbers.size()));
        }});
        structurePie.setSumNum(violations.size());

        return CommonResult.success(structurePie);
    }

    @GetMapping("car-violation-type-structure")
    public CommonResult<?> getCarViolationStructureType() {
        List<Violation> violations = violationServiceIMPL.getAllViolation(new HashMap<>());
        List<String> violationType = new ArrayList<>();
        List<Integer> violationNum = new ArrayList<>();
        for (Violation violation : violations) {
            if (violationType.contains(violation.getV_Type())) {
                int index = violationType.indexOf(violation.getV_Type());
                violationNum.set(index, violationNum.get(index) + 1);
            } else {
                violationType.add(violation.getV_Type());
                violationNum.add(1);
            }
        }

        StructureBar structureBar = new StructureBar();
        structureBar.setTitle("电动车违规分析");
        structureBar.setTypes(violationType);
        structureBar.setValues(violationNum);

        return CommonResult.success(structureBar);
    }
}
