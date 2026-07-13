package com.example.EMS.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EMS.Config.StoredProcedureExecutor;
import com.example.EMS.DTO.RequestDto.EmployeeLoginRequestDto;
import com.example.EMS.DTO.RequestDto.EmployeeRequestDto;
import com.example.EMS.DTO.ResponseDto.EmployeeLoginDataDto;
import com.example.EMS.DTO.ResponseDto.EmployeeLoginResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Security.JwtUtil;
import com.example.EMS.Service.EmployeeService;
import com.example.EMS.Service.RedisService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private StoredProcedureExecutor executor;

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeRequestDto request) {

        List<EmployeeResponseDto> list = executor.query(
                "addEmployee",
                EmployeeResponseDto.class,
                request.getEname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getSalary(),
                request.getDepartmentId());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Unable to add employee");
        }

        return list.get(0);
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Integer id) {

        List<EmployeeResponseDto> list = executor.query(
                "getEmployeeById",
                EmployeeResponseDto.class,
                id);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Employee Not Found");
        }

        return list.get(0);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {

        return executor.query(
                "getAllEmployees",
                EmployeeResponseDto.class);
    }

    @Override
    public EmployeeResponseDto updateEmployee(Integer id,
                                              EmployeeRequestDto request) {

        List<EmployeeResponseDto> list = executor.query(
                "updateEmployee",
                EmployeeResponseDto.class,
                id,
                request.getEname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getSalary(),
                request.getDepartmentId());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Employee Not Found");
        }

        return list.get(0);
    }

    @Override
    public String deleteEmployee(Integer id) {

        int rows = executor.update(
                "deleteEmployee",
                id);

        if (rows == 0) {
            throw new ResourceNotFoundException("Employee Not Found");
        }

        return "Employee Deleted Successfully";
    }

    @Override
    public List<EmployeeResponseDto> searchEmployee(String name) {

        List<EmployeeResponseDto> list = executor.query(
                "searchEmployee",
                EmployeeResponseDto.class,
                name);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Employee Not Found");
        }

        return list;
    }
    
    @Override
    public EmployeeLoginResponseDto employeeLogin(EmployeeLoginRequestDto request) {

        List<EmployeeLoginDataDto> list = executor.query(
                "employeeLogin",
                EmployeeLoginDataDto.class,
                request.getEmail());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Email");
        }

        EmployeeLoginDataDto employee = list.get(0);

        if (!passwordEncoder.matches(
                request.getPassword(),
                employee.getPassword())) {

            throw new ResourceNotFoundException("Invalid Password");
        }

        // Generate JWT
        String token = jwtUtil.generateEmployeeToken(
                employee.getEid(),
                employee.getEmail());

        // Save JWT in Redis
        redisService.saveEmployeeToken(
                employee.getEid(),
                token);

        EmployeeLoginResponseDto response = new EmployeeLoginResponseDto();

        response.setEid(employee.getEid());
        response.setEname(employee.getEname());
        response.setEmail(employee.getEmail());
        response.setToken(token);
        response.setMessage("Login Successful");

        return response;
    }

}