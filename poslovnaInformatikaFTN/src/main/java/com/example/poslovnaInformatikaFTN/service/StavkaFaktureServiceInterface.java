package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;

public interface StavkaFaktureServiceInterface {
	
	public StavkaFakture findOne(Integer id);
	
	public List<StavkaFakture> findAll();
	
	public StavkaFakture save(StavkaFakture stavkaFakture);
	
	public void remove(Integer id);

}
