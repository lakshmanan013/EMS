package com.example.EMS.Model;


import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @NotNull(message="Attendance Date is Required")
    @Column(nullable = false)
    private LocalDate attendanceDate;

    @NotNull(message="Check In Time is Required")
    @Column(nullable = false)
    private LocalTime checkIn;

    @NotNull(message="Check Out Time is Required")
    @Column(nullable = false)
    private LocalTime checkOut;

    @NotNull(message="Status is Required")
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-attendance")
    private Employee employee;

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(
            Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(
            LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(
            LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(
            LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(
            Employee employee) {
        this.employee = employee;
    }
}