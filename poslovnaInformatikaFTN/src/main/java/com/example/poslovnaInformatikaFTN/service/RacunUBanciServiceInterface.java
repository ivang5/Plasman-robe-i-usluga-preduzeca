package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;

public interface RacunUBanciServiceInterface {
	
	public RacunUBanci findOne(Integer id);
	
	public List<RacunUBanci> findAll();
	
	public RacunUBanci save(RacunUBanci racunUBanci);
	
	public void remove(Integer id);

}
