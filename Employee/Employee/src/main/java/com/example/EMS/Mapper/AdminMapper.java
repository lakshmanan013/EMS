package com.example.EMS.Mapper;


import com.example.EMS.DTO.RequestDto.AdminRequestDto;
import com.example.EMS.DTO.ResponseDto.AdminResponseDto;
import com.example.EMS.Model.Admin;

public class AdminMapper {


    public static Admin toEntity(AdminRequestDto request) {

        Admin admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());

        return admin;
    }


    public static AdminResponseDto toResponse(Admin admin) {

        AdminResponseDto response = new AdminResponseDto();

        response.setAdminId(admin.getAdminId());
        response.setUsername(admin.getUsername());



        return response;
    }
}
