package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.PDVKategorijaDTO;
import com.example.poslovnaInformatikaFTN.entity.PDVKategorija;

public interface PDVKategorijaServiceInterface {
	
	public PDVKategorija findOne(Integer id);
	
	public List<PDVKategorijaDTO> findAll();
	
	public void save(PDVKategorijaDTO pdvKategorijaDTO);
	
	public void update(PDVKategorijaDTO pdvKategorijaDTO);
	
	public void remove(Integer id);

}
