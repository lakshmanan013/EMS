package com.example.EMS.Mapper;

import com.example.EMS.DTO.RequestDto.EmployeeRequestDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Model.Department;
import com.example.EMS.Model.Employee;

public class EmployeeMapper {

    public static Employee toEntity(
            EmployeeRequestDto request,
            Department department) {

        Employee employee = new Employee();

        employee.setEname(request.getEname());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setSalary(request.getSalary());
        employee.setDepartment(department);

        return employee;
    }

    public static EmployeeResponseDto toResponse(
            Employee employee) {

        EmployeeResponseDto response =
                new EmployeeResponseDto();

        response.setEid(employee.getEid());
        response.setEname(employee.getEname());
        response.setEmail(employee.getEmail());
        response.setSalary(employee.getSalary());

        if (employee.getDepartment() != null) {

            response.setDepartmentId(
                    employee.getDepartment().getDid());

            response.setDepartmentName(
                    employee.getDepartment().getDname());
        }

        return response;
    }
}

