package com.example.poslovnaInformatikaFTN.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.PreduzeceDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;

@Transactional
@Service
public class PreduzeceService implements PreduzeceServiceInterface {
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public Preduzece findOne(Integer id) {
		return preduzeceRepository.getOne(id);
	}
	@Override
	public List<Preduzece> findAll() {
		return preduzeceRepository.findAll();
	}
	@Override
	public Preduzece save(Preduzece preduzece) {
		return preduzeceRepository.save(preduzece);
	}
	@Override
	public void remove(Integer id) {
		preduzeceRepository.deleteById(id);
	}
	@Override
	public void update(PreduzeceDTO preduzeceDTO) {
		Preduzece preduzece = preduzeceRepository.getOne(preduzeceDTO.getId());
		preduzece.setNaziv(preduzeceDTO.getNaziv());
		preduzece.setPib(preduzeceDTO.getPib());
		preduzece.setTelefon(preduzeceDTO.getTelefon());
		preduzeceRepository.save(preduzece);
	}
	@Override
	public void addRacun(RacunUBanciDTO racunUBanciDTO) {
		Preduzece preduzece = preduzeceRepository.getOne(1);
		Set<RacunUBanci> racuniUBanci = preduzece.getRacuniUBanci();
		RacunUBanci racunUBanci = new RacunUBanci();
		racunUBanci.setBrojRacuna(racunUBanciDTO.getBrojRacuna());
		racunUBanci.setNazivBanke(racunUBanciDTO.getNazivBanke());
		racunUBanci.setObrisan(racunUBanciDTO.isObrisan());
		racuniUBanci.add(racunUBanci);
			
		preduzece.setRacuniUBanci(racuniUBanci);
		
		preduzeceRepository.save(preduzece);
	}
	@Override
	public void deleteRacun(Integer idRacuna) {
		Preduzece preduzece = preduzeceRepository.getOne(1);
		RacunUBanci racunZaBrisanje = null;
		for(RacunUBanci r : preduzece.getRacuniUBanci()) {
			if(r.getId() == idRacuna) {
				racunZaBrisanje = r;
			}
		}
		racunZaBrisanje.setObrisan(true);
		preduzeceRepository.save(preduzece);
	}

}
