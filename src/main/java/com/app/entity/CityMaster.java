package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table (name="City_Master")
public class CityMaster {
	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;

}
