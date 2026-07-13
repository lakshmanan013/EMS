package com.example.EMS.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.ApiResponse.ApiResponse;
import com.example.EMS.DTO.RequestDto.EmployeeLoginRequestDto;
import com.example.EMS.DTO.RequestDto.EmployeeRequestDto;
import com.example.EMS.DTO.ResponseDto.EmployeeLoginResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> saveEmployee(@RequestBody @Valid EmployeeRequestDto request) {

        EmployeeResponseDto employee = employeeService.saveEmployee(request);

        ApiResponse<EmployeeResponseDto> response = new ApiResponse<>
        (HttpStatus.CREATED.value(),
        		"Employee Added Successfully",
        		employee);

        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<EmployeeLoginResponseDto> employeeLogin(
            @RequestBody EmployeeLoginRequestDto request) {

        return ResponseEntity.ok(employeeService.employeeLogin(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployeeById(
            @PathVariable Integer id) {

        EmployeeResponseDto employee = employeeService.getEmployeeById(id);

        ApiResponse<EmployeeResponseDto> response = new ApiResponse<>
        (HttpStatus.OK.value(),
        		"Employee Found By Id",
        		employee);

        return ResponseEntity.ok(response);
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getAllEmployees() {

    	List<EmployeeResponseDto> list =
    	            employeeService.getAllEmployees();

    	    ApiResponse<List<EmployeeResponseDto>> response =
    	            new ApiResponse<>(
    	                    HttpStatus.OK.value(),
    	                    "Employee List",
    	                    list);

    	    return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeRequestDto request) {

    	EmployeeResponseDto employee = employeeService.updateEmployee(id, request);

    	ApiResponse<EmployeeResponseDto> response = new ApiResponse<> (HttpStatus.OK.value(),"Employee Updated Successfully",employee);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(
            @PathVariable Integer id) {

    	employeeService.deleteEmployee(id);

    	ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(),"Employee Deleted Successfully",null);

    	return ResponseEntity.ok(response);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployee(
            @PathVariable String name) {


    	List<EmployeeResponseDto> list =
    	            employeeService.searchEmployee(name);


    	    ApiResponse<List<EmployeeResponseDto>> response =
    	            new ApiResponse<>(
    	                    HttpStatus.OK.value(),
    	                    "Employee Found By Name",
    	                    list);

        return ResponseEntity.ok(response);
    }



}










