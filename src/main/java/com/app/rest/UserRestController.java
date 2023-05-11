package com.app.rest;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.LoginForm;
import com.app.binding.UnlockAccountForm;
import com.app.binding.UserForm;
import com.app.service.UserMgmtService;

@RestController
@CrossOrigin
public class UserRestController {
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/login")
	public ResponseEntity<String > login(@RequestBody LoginForm loginForm){
		String status = userMgmtService.login(loginForm);
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	
	@GetMapping("/countries")
	public Map<Integer,String> loadCountries(){
		return  userMgmtService.getCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadState( @PathVariable  Integer countryId){
		return userMgmtService.getStates(countryId);
		
	}
	
	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable Integer stateId){
		return userMgmtService.getCities(stateId);
	}
	
	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable String email) {
		
		return userMgmtService.checkEmail(email);
	}
	
	@PostMapping("/user")
	public ResponseEntity<String> userRegisteration(@RequestBody  UserForm userForm){
		String status = userMgmtService.registerUser(userForm);
		return new ResponseEntity<>(status,HttpStatus.CREATED);
	}
	
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount( @RequestBody UnlockAccountForm unlockAccForm){
		String status = userMgmtService.unlockAccount(unlockAccForm);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable  String email){
		String status = userMgmtService.forgotPwd(email);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	
	

}
