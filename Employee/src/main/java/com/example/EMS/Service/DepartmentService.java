package com.example.EMS.Service;



import java.util.List;

import com.example.EMS.DTO.RequestDto.DepartmentRequestDto;
import com.example.EMS.DTO.ResponseDto.DepartmentResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;




public interface DepartmentService {

    DepartmentResponseDto saveDepartment(
            DepartmentRequestDto request);

    DepartmentResponseDto getDepartmentById(
            Integer id);

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto updateDepartment(
            Integer id,
            DepartmentRequestDto request);

    String deleteDepartment(
            Integer id);

    List<EmployeeResponseDto> getEmployeesByDepartment(
            Integer id);

    List<DepartmentResponseDto> searchDepartment(String dname);
    
    DepartmentResponseDto getDepartmentByEmployeeId(Integer employeeId);

}
