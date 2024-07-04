package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.Admin;
import com.GOTEL.SchoolCar.Entity.User;
import com.GOTEL.SchoolCar.Entity.Worker;
import com.GOTEL.SchoolCar.Mapper.AdminMapper;
import com.GOTEL.SchoolCar.Mapper.UserMapper;
import com.GOTEL.SchoolCar.Mapper.WorkerMapper;
import com.GOTEL.SchoolCar.Redis.RedisBean;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.AccountService;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import com.GOTEL.SchoolCar.Utils.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountServiceIMPL implements AccountService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private AdminMapper adminMapper;
    private final IToken iToken = new IToken();
    private final IDGenerator idGenerator = new IDGenerator();

    @Override
    public CommonResult<?> login(String loginID, String loginPassword) {
        RedisTemplate redisTemplate = RedisBean.redis;
        Map<String, String> tokenIDMap = redisTemplate.opsForHash().entries("token");
        User user = userMapper.getByUserStudentID(loginID);
        if (user != null && user.getU_Password().equals(loginPassword)) {
            String token = iToken.createToken(user.getUID(), tokenIDMap);
            redisTemplate.opsForHash().put("token", token, user.getUID());
            return CommonResult.successLogin(user.getUID(), "普通用户", token);
        } else {
            Worker worker = workerMapper.getByJobNumber(loginID);
            if (worker != null && worker.getW_Password().equals(loginPassword)) {
                String token = iToken.createToken(worker.getWID(), tokenIDMap);
                redisTemplate.opsForHash().put("token", token, worker.getWID());
                return CommonResult.successLogin(worker.getWID(), "工作人员", token);
            } else {
                Admin admin = adminMapper.getByJobNumber(loginID);
                if (admin != null && admin.getA_Password().equals(loginPassword)) {
                    String token = iToken.createToken(admin.getAID(), tokenIDMap);
                    redisTemplate.opsForHash().put("token", token, admin.getAID());
                    return CommonResult.successLogin(admin.getAID(), "管理员", token);
                }
            }
        }
        return CommonResult.error("登陆失败");
    }

    @Override
    public CommonResult<?> logon(User user) {
        User byUserStudentID = userMapper.getByUserStudentID(user.getU_StudentID());
        if (byUserStudentID == null) {
            user.setUID(idGenerator.generateID(userMapper.getAllUID(), "U"));
            user.setU_Name("swust" + user.getU_StudentID().substring(user.getU_StudentID().length() - 4));
            user.setU_Password("swust" + user.getU_StudentID().substring(user.getU_StudentID().length() - 4));
            int i = userMapper.addUser(user);
            if (i == 1) {
                return CommonResult.success(user, "注册成功");
            } else {
                return CommonResult.error("注册失败");
            }
        } else {
            return CommonResult.error("用户已存在");
        }
    }
}