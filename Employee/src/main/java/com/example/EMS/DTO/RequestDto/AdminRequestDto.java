package com.example.EMS.DTO.RequestDto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AdminRequestDto {

	@NotBlank(message = "Username is required")
	@Size(min =3,max =30,message="Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20,
    message = "Password must be between 6 and 20 characters")
    private String password;

	    public AdminRequestDto() {
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(
	            String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(
	            String password) {
	        this.password = password;
	    }
}
