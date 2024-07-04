package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.Notify;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.NotifyServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("notify")
@RestController
public class NotifyController {
    @Autowired
    private NotifyServiceIMPL notifyServiceIMPL;

    @GetMapping("{id}")
    public CommonResult<?> getByTid(@PathVariable("id") String tid) {
        List<Notify> notifies = notifyServiceIMPL.getByTid(tid);
        if (!notifies.isEmpty()) {
            return CommonResult.success(notifies);
        } else {
            return CommonResult.error("没有通知");
        }
    }
}
