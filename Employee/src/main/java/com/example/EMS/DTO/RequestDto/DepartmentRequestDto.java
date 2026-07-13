package com.example.EMS.DTO.RequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DepartmentRequestDto {


	@NotBlank(message = "Department name is required")
    @Size(min = 2, max = 50,
            message = "Department Name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Department Name should contain only alphabets and spaces")
    private String dname;

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}


}
