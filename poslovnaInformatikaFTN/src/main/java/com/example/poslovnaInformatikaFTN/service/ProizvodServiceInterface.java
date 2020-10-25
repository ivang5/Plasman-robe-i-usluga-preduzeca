package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;

public interface ProizvodServiceInterface {
	
	public Proizvod findOne(Integer id);
	
	public List<ProizvodDTO> findAll();
	
	public void save(ProizvodDTO proizvodDTO);
	
	public void update(ProizvodDTO proizvodDTO);
	
	public void remove(Integer id);

}
