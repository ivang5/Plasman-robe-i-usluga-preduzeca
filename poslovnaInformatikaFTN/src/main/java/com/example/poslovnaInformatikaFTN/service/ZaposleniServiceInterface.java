package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.dto.ZaposleniDTO;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;

public interface ZaposleniServiceInterface {
	
	public Zaposleni findOne(Integer id);
	
	public List<ZaposleniDTO> findAll();
	
	public void save(ZaposleniDTO zaposleniDTO);
	
	public void update(ZaposleniDTO zaposleniDTO);
	
	public void remove(Integer id);

}
