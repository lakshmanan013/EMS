package com.example.EMS.DTO.ResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResponseDto {

	    private Integer attendanceId;

	    private Integer employeeId;

	    private String employeeName;

	    private LocalDate attendanceDate;

	    private LocalTime checkIn;

	    private LocalTime checkOut;

	    private String status;

		public Integer getAttendanceId() {
			return attendanceId;
		}

		public void setAttendanceId(Integer attendanceId) {
			this.attendanceId = attendanceId;
		}

		public Integer getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(Integer employeeId) {
			this.employeeId = employeeId;
		}

		public String getEmployeeName() {
			return employeeName;
		}

		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}

		public LocalDate getAttendanceDate() {
			return attendanceDate;
		}

		public void setAttendanceDate(LocalDate attendanceDate) {
			this.attendanceDate = attendanceDate;
		}

		public LocalTime getCheckIn() {
			return checkIn;
		}

		public void setCheckIn(LocalTime checkIn) {
			this.checkIn = checkIn;
		}

		public LocalTime getCheckOut() {
			return checkOut;
		}

		public void setCheckOut(LocalTime checkOut) {
			this.checkOut = checkOut;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}



}
