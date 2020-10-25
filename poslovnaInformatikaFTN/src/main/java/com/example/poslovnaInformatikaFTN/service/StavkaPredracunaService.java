package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;
import com.example.poslovnaInformatikaFTN.repository.StavkaPredracunaRepository;

@Transactional
@Service
public class StavkaPredracunaService implements StavkaPredracunaServiceInterface{
	
	@Autowired
	StavkaPredracunaRepository stavkaPredracunaRepository;
	
	@Override
	public StavkaPredracuna findOne(Integer id) {
		return stavkaPredracunaRepository.getOne(id);
	}
	@Override
	public List<StavkaPredracuna> findAll() {
		return stavkaPredracunaRepository.findAll();
	}
	@Override
	public StavkaPredracuna save(StavkaPredracuna stavkaPredracuna) {
		return stavkaPredracunaRepository.save(stavkaPredracuna);
	}
	@Override
	public void remove(Integer id) {
		stavkaPredracunaRepository.deleteById(id);
	}

}
