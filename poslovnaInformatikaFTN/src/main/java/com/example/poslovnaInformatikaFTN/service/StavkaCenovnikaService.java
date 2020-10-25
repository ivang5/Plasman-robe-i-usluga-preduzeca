package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;
import com.example.poslovnaInformatikaFTN.repository.StavkaCenovnikaRepository;

@Transactional
@Service
public class StavkaCenovnikaService implements StavkaCenovnikaServiceInterface {
	
	@Autowired
	StavkaCenovnikaRepository stavkaCenovnikaRepository;
	
	@Override
	public StavkaCenovnika findOne(Integer id) {
		return stavkaCenovnikaRepository.getOne(id);
	}
	@Override
	public List<StavkaCenovnika> findAll() {
		return stavkaCenovnikaRepository.findAll();
	}
	@Override
	public StavkaCenovnika save(StavkaCenovnika stavkaCenovnika) {
		return stavkaCenovnikaRepository.save(stavkaCenovnika);
	}
	@Override
	public void remove(Integer id) {
		stavkaCenovnikaRepository.deleteById(id);
	}

}
