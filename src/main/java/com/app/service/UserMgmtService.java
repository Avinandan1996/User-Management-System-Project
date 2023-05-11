package com.app.service;

import java.util.Map;



import com.app.binding.LoginForm;
import com.app.binding.UnlockAccountForm;
import com.app.binding.UserForm;
import com.app.entity.User;


public interface UserMgmtService {
	
	public String checkEmail(String email);
	public Map<Integer, String> getCountries();
	public Map<Integer, String> getStates(Integer countryId);
	public Map<Integer, String> getCities(Integer cityId);
	public String registerUser(UserForm userForm);
	public String unlockAccount(UnlockAccountForm unlockAccForm);
	public String login(LoginForm loginForm);
	public String forgotPwd(String email);

}
