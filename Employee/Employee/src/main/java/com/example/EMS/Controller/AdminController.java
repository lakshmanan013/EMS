package com.example.EMS.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.ApiResponse.ApiResponse;
import com.example.EMS.DTO.RequestDto.AdminRequestDto;
import com.example.EMS.DTO.ResponseDto.AdminResponseDto;
import com.example.EMS.Service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AdminResponseDto>> registerAdmin(
            @RequestBody @Valid AdminRequestDto request) {

        AdminResponseDto admin =
                adminService.saveAdmin(request);

        ApiResponse<AdminResponseDto> response =
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Admin Registered Successfully",
                        admin);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminResponseDto>> login(
            @RequestBody @Valid AdminRequestDto request) {

        AdminResponseDto admin =
                adminService.login(request);

        ApiResponse<AdminResponseDto> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Login Successful",
                        admin);

        return ResponseEntity.ok(response);
    }
}