package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PoslovniPartnerDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;

public interface PoslovniPartnerServiceInterface {
	
	public PoslovniPartner findOne(Integer id);
	
	public List<PoslovniPartnerDTO> findAll();
	
	public void save(PoslovniPartnerDTO poslovniPartnerDTO);
	
	public void update(PoslovniPartnerDTO poslovniPartnerDTO);
	
	public void addRacun(RacunUBanciDTO racunUBanciDTO, Integer idPartnera);
	
	public void deleteRacun(Integer idPartnera, Integer idRacuna);
	
	public void remove(Integer id);

}
