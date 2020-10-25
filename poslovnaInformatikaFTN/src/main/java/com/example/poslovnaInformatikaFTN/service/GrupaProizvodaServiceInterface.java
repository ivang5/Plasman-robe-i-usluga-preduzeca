package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.GrupaProizvodaDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;

public interface GrupaProizvodaServiceInterface {
	
	public GrupaProizvoda findOne(Integer id);
	
	public List<GrupaProizvodaDTO> findAll();
	
	public void save(GrupaProizvodaDTO grupaProizvodaDTO);
	
	public void update(GrupaProizvodaDTO grupaProizvodaDTO);
	
	public boolean remove(Integer id);

}
