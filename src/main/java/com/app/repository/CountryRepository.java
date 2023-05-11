package com.app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster, Serializable>{

}
