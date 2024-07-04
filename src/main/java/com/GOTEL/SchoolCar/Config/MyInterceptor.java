package com.GOTEL.SchoolCar.Config;

import com.GOTEL.SchoolCar.Redis.RedisBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        String id = request.getHeader("id");
        RedisTemplate redisTemplate = RedisBean.redis;

        return redisTemplate.opsForHash().hasKey("token", token)
                && Objects.equals(redisTemplate.opsForHash().get("token", token), id);
    }
}
