package com.example.EMS.Service;

import java.util.List;

import com.example.EMS.DTO.RequestDto.AttendanceRequestDto;
import com.example.EMS.DTO.ResponseDto.AttendanceResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;

public interface AttendanceService {

    AttendanceResponseDto markAttendance(
            AttendanceRequestDto request);

    AttendanceResponseDto getAttendanceById(
            Integer id);

    List<AttendanceResponseDto> getAllAttendance();

    List<AttendanceResponseDto> getAttendanceByEmployee(
            Integer employeeId);

    AttendanceResponseDto updateAttendance(Integer id, AttendanceRequestDto request);

    String deleteAttendance(Integer id);

    List<EmployeeResponseDto>EmployeesWithoutAttendance();
}
