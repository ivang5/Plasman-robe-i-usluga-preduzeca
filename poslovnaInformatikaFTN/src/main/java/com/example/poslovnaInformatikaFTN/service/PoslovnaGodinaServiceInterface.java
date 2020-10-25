package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PoslovnaGodinaDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;

public interface PoslovnaGodinaServiceInterface {
	
	public PoslovnaGodina findOne(Integer id);
	
	public PoslovnaGodinaDTO findOneByGodina(Integer godina);
	
	public List<PoslovnaGodinaDTO> findAll();
	
	public PoslovnaGodina save(PoslovnaGodina poslovnaGodina);
	
	public void remove(Integer id);

}
