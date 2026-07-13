package com.example.EMS.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.EMS.Service.RedisService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            if (!jwtUtil.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Token Expired");
                return;
            }

            Integer id = jwtUtil.extractId(token);
            String role = jwtUtil.extractRole(token);

            String redisToken;

            if ("ADMIN".equals(role)) {
                redisToken = redisService.getAdminToken(id);
            } else {
                redisToken = redisService.getEmployeeToken(id);
            }

            if (redisToken == null || !redisToken.equals(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid Session");
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            jwtUtil.extractUsername(token),
                            null,
                            Collections.singletonList(
                                    new SimpleGrantedAuthority(role)));

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}