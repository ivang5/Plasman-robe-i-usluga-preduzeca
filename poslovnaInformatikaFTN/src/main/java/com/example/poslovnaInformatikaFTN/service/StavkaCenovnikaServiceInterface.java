package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;

public interface StavkaCenovnikaServiceInterface {
	
	public StavkaCenovnika findOne(Integer id);
	
	public List<StavkaCenovnika> findAll();
	
	public StavkaCenovnika save(StavkaCenovnika stavkaCenovnika);
	
	public void remove(Integer id);

}
