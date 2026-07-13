package com.example.EMS.Service;

import com.example.EMS.DTO.RequestDto.AdminRequestDto;
import com.example.EMS.DTO.ResponseDto.AdminResponseDto;


public interface AdminService {

	  AdminResponseDto saveAdmin(AdminRequestDto request);

	    AdminResponseDto login(AdminRequestDto request);
}