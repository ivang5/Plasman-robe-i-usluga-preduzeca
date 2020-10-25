package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.repository.RacunUBanciRepository;

@Transactional
@Service
public class RacunUBanciService implements RacunUBanciServiceInterface {
	
	@Autowired
	RacunUBanciRepository racunUBanciRepository;
	
	@Override
	public RacunUBanci findOne(Integer id) {
		return racunUBanciRepository.getOne(id);
	}
	@Override
	public List<RacunUBanci> findAll() {
		return racunUBanciRepository.findAll();
	}
	@Override
	public RacunUBanci save(RacunUBanci racunUBanci) {
		return racunUBanciRepository.save(racunUBanci);
	}
	@Override
	public void remove(Integer id) {
		racunUBanciRepository.deleteById(id);
	}

}
