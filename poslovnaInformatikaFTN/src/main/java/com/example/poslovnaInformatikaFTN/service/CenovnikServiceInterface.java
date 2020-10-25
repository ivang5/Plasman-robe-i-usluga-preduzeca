package com.example.poslovnaInformatikaFTN.service;

import java.util.Date;
import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.CenovnikDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;

public interface CenovnikServiceInterface {
	
	public Cenovnik findByDatumIzdavanja(Date datumIzdavanja);
	
	public List<CenovnikDTO> findAllByOrderByDatumIzdavanjaDesc();
	
	public Cenovnik findOne(Integer id);
	
	public List<Cenovnik> findAll();
	
	public void save(CenovnikDTO cenovnikDTO);
	
	public void remove(Integer id);

}
