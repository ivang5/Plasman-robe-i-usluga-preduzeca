package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.PoslovnaGodinaDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.repository.PoslovnaGodinaRepository;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;

@Transactional
@Service
public class PoslovnaGodinaService implements PoslovnaGodinaServiceInterface {

	@Autowired
	PoslovnaGodinaRepository poslovnaGodinaRepository;
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public PoslovnaGodina findOne(Integer id) {
		return poslovnaGodinaRepository.getOne(id);
	}
	
	@Override
	public PoslovnaGodinaDTO findOneByGodina(Integer godina) {
		Preduzece preduzece = preduzeceRepository.getOne(1);
		PoslovnaGodinaDTO poslovnaGodinaDTO = null;
		for(PoslovnaGodina poslovnaGodina : preduzece.getPoslovneGodine()) {
			if(poslovnaGodina.getGodina() == godina) {
				poslovnaGodinaDTO = new PoslovnaGodinaDTO(poslovnaGodina);
			}
		}
		return poslovnaGodinaDTO;
	}
	@Override
	public List<PoslovnaGodinaDTO> findAll() {
		Preduzece preduzece = preduzeceRepository.getOne(1);
		List<PoslovnaGodinaDTO> poslovneGodineDTO = new ArrayList<PoslovnaGodinaDTO>();
		for(PoslovnaGodina poslovnaGodina : preduzece.getPoslovneGodine()) {
			poslovneGodineDTO.add(new PoslovnaGodinaDTO(poslovnaGodina));
		}	
		return poslovneGodineDTO;
	}
	@Override
	public PoslovnaGodina save(PoslovnaGodina poslovnaGodina) {
		return poslovnaGodinaRepository.save(poslovnaGodina);
	}
	@Override
	public void remove(Integer id) {
		poslovnaGodinaRepository.deleteById(id);
	}

}
