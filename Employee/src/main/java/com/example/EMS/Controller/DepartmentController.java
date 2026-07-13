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
import com.example.EMS.DTO.RequestDto.DepartmentRequestDto;
import com.example.EMS.DTO.ResponseDto.DepartmentResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;



    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> saveDepartment(
            @RequestBody @Valid DepartmentRequestDto request) {

    	DepartmentResponseDto department = departmentService.saveDepartment(request);

    	ApiResponse<DepartmentResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(),"Department Added Successfully",department);

    	return ResponseEntity.status(HttpStatus.CREATED)
        		.body(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(
            @PathVariable Integer id) {


        DepartmentResponseDto department =
                departmentService.getDepartmentById(id);

        ApiResponse<DepartmentResponseDto> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Department Found By Id",
                        department);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getAllDepartments() {

    	 List<DepartmentResponseDto> list =
                 departmentService.getAllDepartments();

         ApiResponse<List<DepartmentResponseDto>> response =
                 new ApiResponse<>(
                         HttpStatus.OK.value(),
                         "All Department List",
                         list);

         return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> updateDepartment(
            @PathVariable Integer id,
            @RequestBody @Valid DepartmentRequestDto request) {

    	 DepartmentResponseDto department =
                 departmentService.updateDepartment(id, request);

         ApiResponse<DepartmentResponseDto> response =
                 new ApiResponse<>(
                         HttpStatus.OK.value(),
                         "Department Updated Successfully",
                         department);

         return ResponseEntity.ok(response);
     }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(
            @PathVariable Integer id) {

    	 departmentService.deleteDepartment(id);

         ApiResponse<String> response =
                 new ApiResponse<>(
                         HttpStatus.OK.value(),
                         "Department Deleted Successfully",
                         null);

         return ResponseEntity.ok(response);    }



    @GetMapping("/{id}/employees")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getEmployeesByDepartment(
            @PathVariable Integer id) {

    	 List<EmployeeResponseDto> employees =
    	            departmentService.getEmployeesByDepartment(id);

    	    ApiResponse<List<EmployeeResponseDto>> response =
    	            new ApiResponse<>(
    	                    HttpStatus.OK.value(),
    	                    "Employees Retrieved Successfully",
    	                    employees);

    	    return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{dname}")
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> searchDepartment(
            @PathVariable String dname) {

    	List<DepartmentResponseDto> departments =  departmentService.searchDepartment(dname);

    	ApiResponse<List<DepartmentResponseDto>> response = new ApiResponse<>(HttpStatus.OK.value(),"Department Searched By Name Successfully",departments);

    	return ResponseEntity.ok(response);
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentByEmployeeId(
            @PathVariable Integer employeeId) {

        DepartmentResponseDto department =
                departmentService.getDepartmentByEmployeeId(employeeId);

        ApiResponse<DepartmentResponseDto> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Employee Department Found",
                        department);

        return ResponseEntity.ok(response);
    }
}