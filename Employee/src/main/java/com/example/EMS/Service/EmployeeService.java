package com.example.EMS.Service;

import java.util.List;

import com.example.EMS.DTO.RequestDto.EmployeeLoginRequestDto;
import com.example.EMS.DTO.RequestDto.EmployeeRequestDto;
import com.example.EMS.DTO.ResponseDto.EmployeeLoginResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;


public interface EmployeeService {

  public  EmployeeResponseDto saveEmployee(EmployeeRequestDto request);

    EmployeeResponseDto getEmployeeById(Integer id);

    List<EmployeeResponseDto> getAllEmployees();

    EmployeeResponseDto updateEmployee(
            Integer id,
            EmployeeRequestDto request);

    String deleteEmployee(Integer id);

    List<EmployeeResponseDto> searchEmployee(
            String name);

    EmployeeLoginResponseDto employeeLogin(EmployeeLoginRequestDto request);

}