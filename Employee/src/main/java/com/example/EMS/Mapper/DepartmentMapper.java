package com.example.EMS.Mapper;

import com.example.EMS.DTO.RequestDto.DepartmentRequestDto;
import com.example.EMS.DTO.ResponseDto.DepartmentResponseDto;
import com.example.EMS.Model.Department;

public class DepartmentMapper {

    public static Department toEntity(
            DepartmentRequestDto request) {

        Department department =
                new Department();

        department.setDname(
                request.getDname());

        return department;
    }

    public static DepartmentResponseDto toResponse(
            Department department) {

        DepartmentResponseDto response =
                new DepartmentResponseDto();

        response.setDid(
                department.getDid());

        response.setDname(
                department.getDname());

        return response;
    }
}