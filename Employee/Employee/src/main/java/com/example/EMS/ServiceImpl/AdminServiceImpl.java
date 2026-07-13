package com.example.EMS.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EMS.Config.StoredProcedureExecutor;
import com.example.EMS.DTO.RequestDto.AdminLoginDto;
import com.example.EMS.DTO.RequestDto.AdminRequestDto;
import com.example.EMS.DTO.ResponseDto.AdminResponseDto;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Security.JwtUtil;
import com.example.EMS.Service.AdminService;
import com.example.EMS.Service.RedisService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RedisService redisService;
    
	@Autowired
    private StoredProcedureExecutor executor;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminResponseDto saveAdmin(AdminRequestDto request) {

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        List<AdminResponseDto> list =
                executor.query(
                        "registerAdmin",
                        AdminResponseDto.class,
                        request.getUsername(),
                        encodedPassword);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Unable to Register Admin");
        }

        return list.get(0);
    }

    @Override
    public AdminResponseDto login(AdminRequestDto request) {

        List<AdminLoginDto> list =
                executor.query(
                        "adminLogin",
                        AdminLoginDto.class,
                        request.getUsername());

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Username");
        }

        AdminLoginDto admin = list.get(0);

        if (!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())) {

            throw new ResourceNotFoundException("Invalid Password");
        }

        // Generate JWT
        String token = jwtUtil.generateAdminToken(
                admin.getAdminId(),
                admin.getUsername());

        // Save JWT in Redis
        redisService.saveAdminToken(
                admin.getAdminId(),
                token);

        AdminResponseDto response = new AdminResponseDto();

        response.setAdminId(admin.getAdminId());
        response.setUsername(admin.getUsername());
        response.setToken(token);
        response.setMessage("Login Successful");

        return response;
    }
}