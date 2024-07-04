package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.*;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.*;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("car")
@RestController
public class CarController {
    @Autowired
    EAEServiceIMPL eaeServiceIMPL;
    @Autowired
    ViolationServiceIMPL violationServiceIMPL;
    @Autowired
    CarServiceIMPL carServiceIMPL;
    @Autowired
    CarNewServiceIMPL carNewServiceIMPL;
    @Autowired
    MessageServiceIMPL messageServiceIMPL;
    @Autowired
    WorkerServiceIMPL workerServiceIMPL;
    @Autowired
    UserServiceIMPL userServiceIMPL;
    @Autowired
    private CarOldSerViceIMPL carOldSerViceIMPL;
    @Autowired
    ExcelServiceIMPL excelServiceIMPL;
    @Autowired
    private NotifyServiceIMPL notifyServiceIMPL;
    @Autowired
    private EmailServiceIMPL emailServiceIMPL;
    @Autowired
    private Email email;

    IDGenerator idGenerator = new IDGenerator();

    @GetMapping("car-plate-numbers")
    public CommonResult<?> getAllPlateNumber() {
        return CommonResult.success(carServiceIMPL.getAllPlateNumber());
    }

    @GetMapping("car-inf-all")
    public CommonResult<?> getCarInfAll(HttpServletRequest request) {
        Map<String, String> infPickMap = getInfPickMap(request);
        List<Car> cars = carServiceIMPL.getAllCar(infPickMap);
        if (cars.isEmpty()) {
            return CommonResult.error("没有符合该条件的电动车");
        } else {
            return CommonResult.success(cars);
        }
    }

    private Map<String, String> getInfPickMap(HttpServletRequest request) {
        String plateNumberPick = request.getParameter("plateNumberPick");
        String realNamePick = request.getParameter("realNamePick");
        String branchPick = request.getParameter("branchPick");
        String modelPick = request.getParameter("modelPick");
        String typePick = request.getParameter("typePick");
        String colorPick = request.getParameter("colorPick");
        return new HashMap<>() {{
            put("plateNumber", plateNumberPick);
            put("realName", realNamePick);
            put("branch", branchPick);
            put("model", modelPick);
            put("type", typePick);
            put("color", colorPick);
        }};
    }

    @PostMapping("export-car-inf")
    public void exportCarInf(@RequestBody List<Car> cars, HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("车牌号");
            add("车主姓名");
            add("车主学号");
            add("品牌");
            add("型号");
            add("类型");
            add("颜色");
        }};
        data.add(firstRow);
        for (Car car : cars) {
            List<String> row = new ArrayList<>();
            row.add(car.getC_PlateNumber());
            row.add(car.getU_RealName());
            row.add(car.getU_StudentID());
            row.add(car.getC_Brand());
            row.add(car.getC_Model());
            row.add(car.getC_Type());
            row.add(car.getC_Color());
            data.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("电动车信息", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("car-enter-out-data")
    public CommonResult<?> getCarEnterOutDataAll(HttpServletRequest request) throws ParseException {
        String startTime = request.getParameter("startTimePick");
        String endTime = request.getParameter("endTimePick");
        String id = request.getParameter("id");
        List<EAE> results;
        Map<String, String> thePick = getEnterOutPickMap(request);
        if (id.startsWith("U")) {
            results = eaeServiceIMPL.getEAEByUID(id, thePick);
        } else {
            results = eaeServiceIMPL.getAllEAE(thePick);
        }
        return getEnterOutResult(startTime, endTime, results);
    }

    private Map<String, String> getEnterOutPickMap(HttpServletRequest request) {
        String plateNumberPick = request.getParameter("plateNumberPick");
        String wherePick = request.getParameter("wherePick");
        String directionPick = request.getParameter("directionPick");
        return new HashMap<>() {{
            put("plateNumber", plateNumberPick);
            put("where", wherePick);
            put("direction", directionPick);
        }};
    }

    private CommonResult<?> getEnterOutResult(String startTime, String endTime, List<EAE> eAEs) throws ParseException {
        List<EAE> results = new ArrayList<>();
        if (!eAEs.isEmpty()) {
            if (startTime != null && endTime != null) {
                for (EAE eAE : eAEs) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(String.valueOf(eAE.getE_Time()));
                    Date start = simpleDateFormat.parse(startTime);
                    Date end = simpleDateFormat.parse(endTime);
                    if (!date.after(end) && !date.before(start)) {
                        results.add(eAE);
                    }
                }
            } else {
                results = eAEs;
            }
            return CommonResult.success(results);
        } else {
            return CommonResult.error("没有符合此条件的进出记录");
        }
    }

    @PostMapping("add-enter-out")
    private void insertEnterOut(@RequestBody EAE eae) {
        eae.setEID(idGenerator.generateID(eaeServiceIMPL.getAllEid(), "E"));
        eae.setCID(carServiceIMPL.getCidByPlate(eae.getC_PlateNumber()));
        eaeServiceIMPL.insertEAE(eae);
    }

    @PostMapping("export-car-enter-out")
    public void exportCarEnterOut(@RequestBody List<EAE> eaes, HttpServletResponse response) throws IOException, ParseException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("车牌号");
            add("通过时间");
            add("闸门位置");
            add("方向");
        }};
        data.add(firstRow);
        for (EAE eae : eaes) {
            List<String> row = new ArrayList<>();
            row.add(eae.getC_PlateNumber());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(String.valueOf(eae.getE_Time()));
            row.add(simpleDateFormat.format(date));
            row.add(eae.getE_Where());
            row.add(eae.getE_Direction());
            data.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("电动车出入记录", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("car-violation-data")
    public CommonResult<?> getCarViolationData(HttpServletRequest request) throws ParseException {
        String startTime = request.getParameter("startTimePick");
        String endTime = request.getParameter("endTimePick");
        String id = request.getParameter("id");
        List<Violation> results;
        Map<String, String> pickMap = getViolationPickMap(request);
        if (id.startsWith("U")) {
            results = violationServiceIMPL.getViolationByUID(id, pickMap);
        } else {
            results = violationServiceIMPL.getAllViolation(pickMap);
        }
        return getViolationResult(startTime, endTime, results);
    }

    private Map<String, String> getViolationPickMap(HttpServletRequest request) {
        String plateNumber = request.getParameter("plateNumberPick");
        String wRealName = request.getParameter("wRealNamePick");
        String type = request.getParameter("typePick");
        String where = request.getParameter("wherePick");
        return new HashMap<>() {{
            put("plateNumber", plateNumber);
            put("wRealName", wRealName);
            put("type", type);
            put("where", where);
        }};
    }

    private CommonResult<?> getViolationResult(String startTime, String endTime, List<Violation> violations) throws ParseException {
        List<Violation> results = new ArrayList<>();
        if (!violations.isEmpty()) {
            if (startTime != null && endTime != null) {
                for (Violation violation : violations) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(String.valueOf(violation.getV_Time()));
                    Date start = simpleDateFormat.parse(startTime);
                    Date end = simpleDateFormat.parse(endTime);
                    if (!date.after(end) && !date.before(start)) {
                        results.add(violation);
                    }
                }
            } else {
                results = violations;
            }
            return CommonResult.success(results);
        } else {
            return CommonResult.error("没有符合此条件的违规信息");
        }
    }

    @PostMapping("export-car-violation")
    public void exportCarViolation(@RequestBody List<Violation> violations, HttpServletResponse response) throws IOException, ParseException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("车牌号");
            add("办理人员");
            add("时间");
            add("地点");
            add("违规类型");
            add("描述");
            add("处罚");
        }};
        data.add(firstRow);
        for (Violation violation : violations) {
            List<String> row = new ArrayList<>();
            row.add(violation.getC_PlateNumber());
            row.add(violation.getW_RealName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(String.valueOf(violation.getV_Time()));
            row.add(simpleDateFormat.format(date));
            row.add(violation.getV_Where());
            row.add(violation.getV_Type());
            row.add(violation.getV_Description());
            row.add(violation.getV_Punish());
            data.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("电动车违规记录", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    @PostMapping("insert-violation")
    public CommonResult<?> insertViolation(HttpServletRequest request, @RequestBody Violation violation) {
        String sendEmail = request.getParameter("sendEmail");
        String vid = idGenerator.generateID(violationServiceIMPL.getAllVid(), "V");
        violation.setVID(vid);
        String cPlateNumber = violation.getC_PlateNumber();
        String cid = carServiceIMPL.getCidByPlate(cPlateNumber);
        violation.setCID(cid);
        boolean b = violationServiceIMPL.insertViolation(violation);
        if (b) {
            createNotify(carServiceIMPL.getUidByCid(cid), "违规通知", "您车辆 " + cPlateNumber + " 在 " + violation.getV_Where() + " 发生 " + violation.getV_Type() + " ，请及时处理。");
            if (sendEmail.equals("true")) {
                email.setTo(userServiceIMPL.selectEmailByPlate(cPlateNumber));
                email.setSubject("电动车违规通知");
                email.setText("您的电动车 " + cPlateNumber + " 在 " + violation.getV_Where() + " 发生 " + violation.getV_Type() + " 违规，请及时处理。");
                if (!emailServiceIMPL.sendEmail(email)) {
                    return CommonResult.error("邮箱发送失败");
                }
            }
        } else {
            return CommonResult.error("违规记录添加失败");
        }
        return CommonResult.success();
    }

    @GetMapping("car-information-data")
    public CommonResult<?> getCarInformationData(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        Car car = carServiceIMPL.getCarByUID(uid);

        if (car != null) {
            return CommonResult.success(car);
        } else {
            return CommonResult.error("此用户暂无车辆入库");
        }
    }

    @GetMapping("car-information-new-data")
    public CommonResult<?> getCarNewInformationData(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        Timestamp timestamp = Timestamp.valueOf(time);
        CarNew byUID = carNewServiceIMPL.getByUIDAndTime(uid, timestamp);

        if (byUID != null) {
            return CommonResult.success(byUID);
        } else {
            return CommonResult.error("此用户暂无车辆入库");
        }
    }

    @GetMapping("car-inf-old-data")
    public CommonResult<?> getCarOldInfData(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        Timestamp timestamp = Timestamp.valueOf(time);
        CarOld carOld = carOldSerViceIMPL.selectByUidAndTime(uid, timestamp);
        if (carOld != null) {
            return CommonResult.success(carOld);
        }
        return CommonResult.error();
    }

    @PostMapping("car-update")
    public CommonResult<?> wantUpdateCar(@RequestBody CarNew carNew) {
        String studentID = carNew.getU_StudentID();
        User user = userServiceIMPL.getUserByUserStudentID(studentID);
        String uid = user.getUID();
        List<Message> messages = messageServiceIMPL.getByUID(uid);
        for (Message message : messages) {
            if (message.getM_Type().equals("电动车信息更新") && message.getM_State().equals("0")) {
                return CommonResult.error("存在未审核申请,不要重复提交");
            }
        }
        Car carByUID = carServiceIMPL.getCarByUID(uid);
        carNew.setCID(carByUID.getCID());
        carNew.setUID(uid);
        LocalDateTime now = LocalDateTime.now();
        carNew.setC_Time(Timestamp.valueOf(now));
        Boolean insertCarNew = carNewServiceIMPL.insertCarNew(carNew);
        if (insertCarNew) {
            return createMessage(carNew, uid, Timestamp.valueOf(now));
        } else {
            return CommonResult.error("创建新信息入库失败");
        }
    }

    @PostMapping("car-create")
    public CommonResult<?> wantCreateCar(@RequestBody CarNew carNew) {
        String studentID = carNew.getU_StudentID();
        User user = userServiceIMPL.getUserByUserStudentID(studentID);
        String uid = user.getUID();
        Car car = carServiceIMPL.getCarByUID(uid);
        if (car != null) {
            return CommonResult.error("已存在电动车");
        }
        List<Message> messages = messageServiceIMPL.getByUID(uid);
        for (Message message : messages) {
            if (message.getM_Type().equals("电动车信息入库") && message.getM_State().equals("0")) {
                return CommonResult.error("存在未审核申请,不要重复提交");
            }
        }
        List<Car> allCar = carServiceIMPL.getAllCar(new HashMap<>());
        List<String> haven = new ArrayList<>();
        for (Car car1 : allCar) {
            haven.add(car1.getCID());
        }
        String cid = idGenerator.generateID(haven, "C");
        carNew.setCID(cid);
        carNew.setUID(uid);
        LocalDateTime now = LocalDateTime.now();
        carNew.setC_Time(Timestamp.valueOf(now));
        Boolean insertCarNew = carNewServiceIMPL.insertCarNew(carNew);
        if (insertCarNew) {
            return createMessage(carNew, uid, Timestamp.valueOf(now));
        } else {
            return CommonResult.error("创建新信息入库失败");
        }
    }

    private CommonResult<?> createMessage(CarNew carNew, String uid, Timestamp dateTime) {
        Message message = new Message();
        List<Message> allMessage = messageServiceIMPL.getAllMessage();
        List<String> haven1 = new ArrayList<>();
        for (Message meg : allMessage) {
            haven1.add(meg.getMID());
        }
        message.setMID(idGenerator.generateID(haven1, "M"));
        message.setUID(uid);
        message.setM_State("0");
        List<Worker> allWorker = workerServiceIMPL.getAllWorker(new HashMap<>());
        message.setWID(allWorker.get(new Random().nextInt(allWorker.size())).getWID());
        message.setM_Type(carNew.getM_Type());
        message.setM_Description(carNew.getM_Type());
        message.setM_Evidence(carNew.getC_Evidence());
        message.setM_CreateTime(dateTime);
        message.setM_UpdateTime(dateTime);
        boolean b1 = messageServiceIMPL.insertMessage(message);
        if (b1) {
            createNotify(message.getWID(), "待处理通知", "您有一条 " + message.getM_Type() + " 新申请，请及时处理");
            return CommonResult.success();
        } else {
            return CommonResult.error("创建申请信息失败");
        }
    }

    private void createNotify(String tid, String title, String content) {
        Notify notify = new Notify();
        notify.setId(idGenerator.generateID(notifyServiceIMPL.getAllId(), "NT"));
        notify.setFid("0");
        notify.setTid(tid);
        notify.setTitle(title);
        notify.setContent(content);
        notify.setTime(Timestamp.valueOf(LocalDateTime.now()));
        notify.setContent_type("text");
        notifyServiceIMPL.insert(notify);
    }


}
