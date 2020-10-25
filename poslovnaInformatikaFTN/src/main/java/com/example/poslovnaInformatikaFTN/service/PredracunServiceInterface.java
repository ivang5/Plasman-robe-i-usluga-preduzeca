package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PredracunDTO;
import com.example.poslovnaInformatikaFTN.entity.Predracun;

public interface PredracunServiceInterface {
	
	public Predracun findOne(Integer id);
	
	public List<PredracunDTO> findAll();
	
	public void save(PredracunDTO predracunDTO);
	
	public void remove(Integer id);

}
