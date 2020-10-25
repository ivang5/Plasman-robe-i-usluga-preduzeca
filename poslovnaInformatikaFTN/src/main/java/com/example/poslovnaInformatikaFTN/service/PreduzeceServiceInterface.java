package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PreduzeceDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;

public interface PreduzeceServiceInterface {
	
	public Preduzece findOne(Integer id);
	
	public List<Preduzece> findAll();
	
	public Preduzece save(Preduzece preduzece);
	
	public void update(PreduzeceDTO preduzeceDTO);
	
	public void addRacun(RacunUBanciDTO racunUBanciDTO);
	
	public void deleteRacun(Integer idRacuna);
	
	public void remove(Integer id);

}
