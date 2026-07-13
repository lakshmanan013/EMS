package com.example.EMS.Mapper;

import com.example.EMS.DTO.RequestDto.AttendanceRequestDto;
import com.example.EMS.DTO.ResponseDto.AttendanceResponseDto;
import com.example.EMS.Model.Attendance;
import com.example.EMS.Model.Employee;

public class AttendanceMapper {


	public static Attendance toEntity(AttendanceRequestDto request, Employee employee){

	        Attendance attendance = new Attendance();

	        attendance.setAttendanceDate(request.getAttendanceDate());
	        attendance.setCheckIn(request.getCheckIn());
	        attendance.setCheckOut(request.getCheckOut());
	        attendance.setStatus(request.getStatus());
	        attendance.setEmployee(employee);

	        return attendance;

	    }

	    public static AttendanceResponseDto toResponse(Attendance attendance){

	        AttendanceResponseDto response = new AttendanceResponseDto();

	        response.setAttendanceId(attendance.getAttendanceId());
	        response.setAttendanceDate(attendance.getAttendanceDate());
	        response.setCheckIn(attendance.getCheckIn());
	        response.setCheckOut(attendance.getCheckOut());
	        response.setStatus(attendance.getStatus());

	        if(attendance.getEmployee()!=null){

	            response.setEmployeeId(attendance.getEmployee().getEid());
	            response.setEmployeeName(attendance.getEmployee().getEname());

	        }

	        return response;

	    }

	}

