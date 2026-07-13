package com.example.EMS.DTO.RequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AttendanceRequestDto {


		@NotNull(message ="Employee Id is Required")
	    private Integer employeeId;

		@NotNull(message = "Attendance Date is Required")
	    private LocalDate attendanceDate;

		@NotNull(message = "Check In Time is Required")
	    private LocalTime checkIn;

		@NotNull(message = "Check Out Time is Required")
	    private LocalTime checkOut;

	    @NotBlank(message = "Attendance Status is Required")
	    private String status;

		public Integer getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(Integer employeeId) {
			this.employeeId = employeeId;
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
