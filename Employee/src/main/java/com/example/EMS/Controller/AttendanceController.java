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
import com.example.EMS.DTO.RequestDto.AttendanceRequestDto;
import com.example.EMS.DTO.ResponseDto.AttendanceResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;


    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceResponseDto>> markAttendance(
            @RequestBody @Valid AttendanceRequestDto request) {

    	AttendanceResponseDto attendance = attendanceService.markAttendance(request);

    	ApiResponse<AttendanceResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(),"Attendance Marked Successfully",attendance);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponseDto>> getAttendanceById(
            @PathVariable Integer id) {

    	AttendanceResponseDto attendance = attendanceService.getAttendanceById(id);

    	ApiResponse<AttendanceResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(),"Attendance Retrieved Successfully",attendance);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceResponseDto>>> getAllAttendance() {

    	List<AttendanceResponseDto> attendanceList =
                attendanceService.getAllAttendance();

        ApiResponse<List<AttendanceResponseDto>> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Attendance List Retrieved Successfully",
                        attendanceList);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponseDto>>> getAttendanceByEmployee(
            @PathVariable Integer employeeId) {

    	 List<AttendanceResponseDto> attendanceList =
    	            attendanceService.getAttendanceByEmployee(employeeId);

    	    ApiResponse<List<AttendanceResponseDto>> response =
    	            new ApiResponse<>(
    	                    HttpStatus.OK.value(),
    	                    "Employee Attendance Retrieved Successfully",
    	                    attendanceList);

    	    return ResponseEntity.ok(response);    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponseDto>> updateAttendance(
            @PathVariable Integer id,
            @RequestBody @Valid AttendanceRequestDto request) {

       AttendanceResponseDto attendance = attendanceService.updateAttendance(id, request);

       ApiResponse<AttendanceResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(),"Attendance Updated Successfully",attendance);


        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAttendance(@PathVariable Integer id) {

    	 attendanceService.deleteAttendance(id);

    	    ApiResponse<String> response =
    	            new ApiResponse<>(
    	                    HttpStatus.OK.value(),
    	                    "Attendance Deleted Successfully",
    	                    null);

    	    return ResponseEntity.ok(response);
    }

    @GetMapping("/empty")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> EmployeesWithoutAttendance() {

        List<EmployeeResponseDto> employees =
                attendanceService.EmployeesWithoutAttendance();

        ApiResponse<List<EmployeeResponseDto>> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Employees With Empty Attendance Retrieved Successfully",
                        employees);

        return ResponseEntity.ok(response);
    }
}
