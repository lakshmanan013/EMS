package com.example.EMS.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EMS.Config.StoredProcedureExecutor;
import com.example.EMS.DTO.RequestDto.DepartmentRequestDto;
import com.example.EMS.DTO.ResponseDto.DepartmentResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Service.DepartmentService;
import com.example.EMS.Service.EmployeeService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private StoredProcedureExecutor executor;

    @Autowired
    private EmployeeService employeeService;
    
    @Override
    public DepartmentResponseDto saveDepartment(DepartmentRequestDto request) {

        List<DepartmentResponseDto> list = executor.query(
                "addDepartment",
                DepartmentResponseDto.class,
                request.getDname());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Unable to add department");
        }

        return list.get(0);
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Integer id) {

        List<DepartmentResponseDto> list = executor.query(
                "getDepartmentById",
                DepartmentResponseDto.class,
                id);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Department Not Found");
        }

        return list.get(0);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {

        return executor.query(
                "getAllDepartments",
                DepartmentResponseDto.class);
    }

    @Override
    public DepartmentResponseDto updateDepartment(Integer id,
                                                  DepartmentRequestDto request) {

        List<DepartmentResponseDto> list = executor.query(
                "updateDepartment",
                DepartmentResponseDto.class,
                id,
                request.getDname());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Department Not Found");
        }

        return list.get(0);
    }

    @Override
    public String deleteDepartment(Integer id) {

        int rows = executor.update(
                "deleteDepartment",
                id);

        if (rows == 0) {
            throw new ResourceNotFoundException("Department Not Found");
        }

        return "Department Deleted Successfully";
    }

    @Override
    public List<DepartmentResponseDto> searchDepartment(String name) {

        List<DepartmentResponseDto> list = executor.query(
                "searchDepartment",
                DepartmentResponseDto.class,
                name);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Department Not Found");
        }

        return list;
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByDepartment(Integer id) {

        List<EmployeeResponseDto> list = executor.query(
                "employeesByDepartment",
                EmployeeResponseDto.class,
                id);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No Employees Found");
        }

        return list;
    }
    
    @Override
    public DepartmentResponseDto getDepartmentByEmployeeId(Integer employeeId) {

        EmployeeResponseDto employee =
                employeeService.getEmployeeById(employeeId);

        if (employee.getDepartmentId() == null) {
            throw new ResourceNotFoundException("Employee Has No Assigned Department");
        }

        return getDepartmentById(employee.getDepartmentId());
    }
}