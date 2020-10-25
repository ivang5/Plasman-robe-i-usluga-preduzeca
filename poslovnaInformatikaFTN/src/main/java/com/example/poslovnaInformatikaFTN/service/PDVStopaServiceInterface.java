package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PDVStopaDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.PDVStopa;

public interface PDVStopaServiceInterface {
	
	public PDVStopa findOne(Integer id);
	
	public List<PDVStopaDTO> findAll();
	
	public PDVStopa save(PDVStopa pDVStopa);
	
	public void remove(Integer id);

}
