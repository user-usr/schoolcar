package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.*;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.*;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private CarNewServiceIMPL carNewServiceIMPL;
    @Autowired
    private CarServiceIMPL carServiceIMPL;
    @Autowired
    private MessageServiceIMPL messageServiceIMPL;
    @Autowired
    private CarOldSerViceIMPL carOldSerViceIMPL;
    @Autowired
    private NotifyServiceIMPL notifyServiceIMPL;
    private final IDGenerator idGenerator = new IDGenerator();

    @GetMapping("getAll")
    public CommonResult<?> getAllByUID(HttpServletRequest request) {
        String id = request.getHeader("id");
        List<Message> messages = new ArrayList<>();
        List<Message> results = new ArrayList<>();
        String state = request.getParameter("state");
        if (id.startsWith("U")) {
            messages = messageServiceIMPL.getByUID(id);
        } else if (id.startsWith("W")) {
            messages = messageServiceIMPL.getByWID(id);
        }
        switch (state) {
            case "doing" -> {
                for (Message message : messages) {
                    if (message.getM_State().equals("0")) {
                        results.add(message);
                    }
                }
            }
            case "done" -> {
                for (Message message : messages) {
                    if (message.getM_State().equals("1") || message.getM_State().equals("2")) {
                        results.add(message);
                    }
                }
            }
            case "all" -> results = messages;
        }

        return CommonResult.success(results);
    }

    @PostMapping("doYes")
    public CommonResult<?> doYes(@RequestBody Message messageRequest) {
        MessageDemo messageDemo = getMessageDemo(messageRequest);
        CarNew carNew = carNewServiceIMPL.getByUIDAndTime(messageDemo.uid(), messageDemo.message().getM_CreateTime());
        if (messageDemo.message().getM_Type().equals("电动车信息更新")) {
            Car car = carServiceIMPL.getCarByUID(messageDemo.uid());
            CarOld carOld = new CarOld();
            carOld.setCID(car.getCID());
            carOld.setUID(car.getUID());
            carOld.setC_PlateNumber(car.getC_PlateNumber());
            carOld.setC_Brand(car.getC_Brand());
            carOld.setC_Model(car.getC_Model());
            carOld.setC_Type(car.getC_Type());
            carOld.setC_Color(car.getC_Color());
            carOld.setC_Time(messageDemo.message().getM_CreateTime());
            carOldSerViceIMPL.insertCarOld(carOld);

            carServiceIMPL.updateCar(carNew);
        } else if (messageDemo.message().getM_Type().equals("电动车信息入库")) {
            carServiceIMPL.insertCar(carNew);
        }
        boolean b = messageServiceIMPL.doYes(messageDemo.map());
        if (b) {
            createNotify(messageDemo.uid(), "审核完成", "您关于 " + messageDemo.message().getM_Type() + " 的申请已审核，请前往查看");
            return CommonResult.success();
        }
        return CommonResult.error();
    }

    @PostMapping("doNo")
    public CommonResult<?> doNo(@RequestBody Message messageRequest) {
        MessageDemo messageDemo = getMessageDemo(messageRequest);
        if (messageDemo.message().getM_Type().equals("电动车信息更新")) {
            Car car = carServiceIMPL.getCarByUID(messageDemo.uid());
            CarOld carOld = new CarOld();
            carOld.setCID(car.getCID());
            carOld.setUID(car.getUID());
            carOld.setC_PlateNumber(car.getC_PlateNumber());
            carOld.setC_Brand(car.getC_Brand());
            carOld.setC_Model(car.getC_Model());
            carOld.setC_Type(car.getC_Type());
            carOld.setC_Color(car.getC_Color());
            carOld.setC_Time(messageDemo.message().getM_CreateTime());
            carOldSerViceIMPL.insertCarOld(carOld);
        }
        boolean b = messageServiceIMPL.doNo(messageDemo.map());
        if (b) {
            createNotify(messageDemo.uid(), "审核完成", "您关于 " + messageDemo.message().getM_Type() + " 的申请已审核，请前往查看");
            return CommonResult.success();
        }
        return CommonResult.error();
    }

    private record MessageDemo(Map<String, String> map, Message message, String uid) {
    }

    private MessageDemo getMessageDemo(Message messageRequest) {
        String mid = messageRequest.getMID();
        String description = messageRequest.getM_Description();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = simpleDateFormat.format(new Date());
        Map<String, String> map = new HashMap<>() {{
            put("mid", mid);
            put("updateTime", updateTime);
            put("description", description);
        }};
        Message message = messageServiceIMPL.getMessageByMID(mid);
        String uid = message.getUID();
        return new MessageDemo(map, message, uid);
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
