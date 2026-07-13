package com.example.EMS.DTO.ResponseDto;

public class EmployeeLoginResponseDto {
	private Integer eid;
	private String ename;
	private String email;
	private String message;
	private String token;
	
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
	    return token;
	}

	public void setToken(String token) {
	    this.token = token;
	}	
}
