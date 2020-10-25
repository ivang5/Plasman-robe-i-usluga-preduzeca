package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.repository.StavkaFaktureRepository;

@Transactional
@Service
public class StavkaFaktureService implements StavkaFaktureServiceInterface {
	
	@Autowired
	StavkaFaktureRepository stavkaFaktureRepository;
	
	@Override
	public StavkaFakture findOne(Integer id) {
		return stavkaFaktureRepository.getOne(id);
	}
	@Override
	public List<StavkaFakture> findAll() {
		return stavkaFaktureRepository.findAll();
	}
	@Override
	public StavkaFakture save(StavkaFakture stavkaFakture) {
		return stavkaFaktureRepository.save(stavkaFakture);
	}
	@Override
	public void remove(Integer id) {
		stavkaFaktureRepository.deleteById(id);
	}

}
