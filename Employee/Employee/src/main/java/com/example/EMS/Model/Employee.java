package com.example.EMS.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eid;

    @NotBlank(message ="Employee name is required")
    private String ename;

    @Email(message="Invalid Email")
    @NotBlank(message = "Email is Required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    @NotNull(message ="Salary is Required")
    @Positive(message ="Salary must be greater than 0")
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "did")
    @JsonBackReference("department-employee")
    private Department department;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL)
    @JsonManagedReference("employee-attendance")
    private List<Attendance> attendances =
            new ArrayList<>();


    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password) {
        this.password = password;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(
            Double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(
            Department department) {
        this.department = department;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(
            List<Attendance> attendances) {
        this.attendances = attendances;
    }


}