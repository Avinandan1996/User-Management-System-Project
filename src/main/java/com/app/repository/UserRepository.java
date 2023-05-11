package com.app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable>{
	
	//select * from User_Master where email=?
	public User findByEmail(String email);
	
	//Select * from User_Master where email=? and user_pwd=? 
	public User findByEmailAndUserPwd(String Email, String Pwd);

}
