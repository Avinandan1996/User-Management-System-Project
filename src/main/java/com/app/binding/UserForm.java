package com.app.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserForm {
	
	private Integer userId;
	private String fname;
	private String lname;
	private String email;
	private Long phno;
	private LocalDate dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityid;

}
