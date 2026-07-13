package com.example.EMS.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EMS.Config.StoredProcedureExecutor;
import com.example.EMS.DTO.RequestDto.AttendanceRequestDto;
import com.example.EMS.DTO.ResponseDto.AttendanceResponseDto;
import com.example.EMS.DTO.ResponseDto.EmployeeResponseDto;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private StoredProcedureExecutor executor;

    @Override
    public AttendanceResponseDto markAttendance(AttendanceRequestDto request) {

        List<AttendanceResponseDto> list = executor.query(
                "markAttendance",
                AttendanceResponseDto.class,
                request.getEmployeeId(),
                request.getAttendanceDate(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getStatus());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Unable to Mark Attendance");
        }

        return list.get(0);
    }

    @Override
    public AttendanceResponseDto getAttendanceById(Integer id) {

        List<AttendanceResponseDto> list = executor.query(
                "getAttendanceById",
                AttendanceResponseDto.class,
                id);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Attendance Not Found");
        }

        return list.get(0);
    }

    @Override
    public List<AttendanceResponseDto> getAllAttendance() {

        return executor.query(
                "getAllAttendance",
                AttendanceResponseDto.class);
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceByEmployee(Integer employeeId) {

        List<AttendanceResponseDto> list = executor.query(
                "attendanceByEmployee",
                AttendanceResponseDto.class,
                employeeId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Attendance Not Found");
        }

        return list;
    }

    @Override
    public AttendanceResponseDto updateAttendance(Integer id,
                                                  AttendanceRequestDto request) {

        List<AttendanceResponseDto> list = executor.query(
                "updateAttendance",
                AttendanceResponseDto.class,
                id,
                request.getEmployeeId(),
                request.getAttendanceDate(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getStatus());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Attendance Not Found");
        }

        return list.get(0);
    }

    @Override
    public String deleteAttendance(Integer id) {

        int rows = executor.update(
                "deleteAttendance",
                id);

        if (rows == 0) {
            throw new ResourceNotFoundException("Attendance Not Found");
        }

        return "Attendance Deleted Successfully";
    }

    @Override
    public List<EmployeeResponseDto> EmployeesWithoutAttendance() {

        List<EmployeeResponseDto> list = executor.query(
                "employeesWithoutAttendance",
                EmployeeResponseDto.class);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No Employees Found");
        }

        return list;
    }
}