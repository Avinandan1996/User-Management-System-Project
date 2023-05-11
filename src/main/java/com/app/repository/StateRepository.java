package com.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.StateMaster;

public interface StateRepository extends JpaRepository<StateMaster, Serializable> {
	
	//select * from states_master where countryId= ?
	public List<StateMaster> findByCountryId(Integer countryId);

}
