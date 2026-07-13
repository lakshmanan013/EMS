package com.example.EMS.DTO.ResponseDto;

public class AdminResponseDto {

		private String token;
	
		private String message;
	
	    private Integer adminId;

	    private String username;


		public Integer getAdminId() {
			return adminId;
		}

		public void setAdminId(Integer adminId) {
			this.adminId = adminId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getToken() {
		    return token;
		}

		public void setToken(String token) {
		    this.token = token;
		}

		public String getMessage() {
		    return message;
		}

		public void setMessage(String message) {
		    this.message = message;
		}

}
