package com.example.EMS.ServiceImpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EMS.Service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Duration TOKEN_EXPIRY = Duration.ofMinutes(15);

    /* ==========================
       Employee
       ========================== */

    @Override
    public void saveEmployeeToken(Integer employeeId, String token) {

        redisTemplate.opsForValue().set(
                "EMPLOYEE:" + employeeId,
                token,
                TOKEN_EXPIRY);
    }

    @Override
    public String getEmployeeToken(Integer employeeId) {

        return redisTemplate.opsForValue().get(
                "EMPLOYEE:" + employeeId);
    }

    @Override
    public void deleteEmployeeToken(Integer employeeId) {

        redisTemplate.delete(
                "EMPLOYEE:" + employeeId);
    }

    /* ==========================
       Admin
       ========================== */

    @Override
    public void saveAdminToken(Integer adminId, String token) {

        redisTemplate.opsForValue().set(
                "ADMIN:" + adminId,
                token,
                TOKEN_EXPIRY);
    }

    @Override
    public String getAdminToken(Integer adminId) {

        return redisTemplate.opsForValue().get(
                "ADMIN:" + adminId);
    }

    @Override
    public void deleteAdminToken(Integer adminId) {

        redisTemplate.delete(
                "ADMIN:" + adminId);
    }

}