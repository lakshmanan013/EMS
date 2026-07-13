package com.example.EMS.Service;

public interface RedisService {

    // Employee
    void saveEmployeeToken(Integer employeeId, String token);

    String getEmployeeToken(Integer employeeId);

    void deleteEmployeeToken(Integer employeeId);

    // Admin
    void saveAdminToken(Integer adminId, String token);

    String getAdminToken(Integer adminId);

    void deleteAdminToken(Integer adminId);

}