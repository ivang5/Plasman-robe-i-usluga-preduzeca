package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;


public interface StavkaPredracunaServiceInterface {
	
	public StavkaPredracuna findOne(Integer id);
	
	public List<StavkaPredracuna> findAll();
	
	public StavkaPredracuna save(StavkaPredracuna stavkaPredracuna);
	
	public void remove(Integer id);

}
