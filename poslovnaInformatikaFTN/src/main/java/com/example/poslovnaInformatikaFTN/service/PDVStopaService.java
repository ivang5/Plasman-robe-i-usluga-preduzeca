package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.PDVStopaDTO;
import com.example.poslovnaInformatikaFTN.entity.PDVStopa;
import com.example.poslovnaInformatikaFTN.repository.PDVStopaRepository;

@Transactional
@Service
public class PDVStopaService implements PDVStopaServiceInterface {
	
	@Autowired
	PDVStopaRepository pDVStopaRepository;
	
	@Override
	public PDVStopa findOne(Integer id) {
		return pDVStopaRepository.getOne(id);
	}
	@Override
	public List<PDVStopaDTO> findAll() {
		List<PDVStopaDTO> pdvStopeDTO = new ArrayList<PDVStopaDTO>();
		for(PDVStopa pdv : pDVStopaRepository.findAll()) {
			if(!pdv.isObrisan())
				pdvStopeDTO.add(new PDVStopaDTO(pdv));
		}
		return pdvStopeDTO;
	}
	@Override
	public PDVStopa save(PDVStopa pDVStopa) {
		return pDVStopaRepository.save(pDVStopa);
	}
	@Override
	public void remove(Integer id) {
		pDVStopaRepository.deleteById(id);
	}

}
