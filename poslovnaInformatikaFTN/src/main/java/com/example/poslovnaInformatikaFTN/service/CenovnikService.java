package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.CenovnikDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaCenovnikaDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;
import com.example.poslovnaInformatikaFTN.repository.CenovnikRepository;
import com.example.poslovnaInformatikaFTN.repository.ProizvodRepository;

@Transactional
@Service
public class CenovnikService implements CenovnikServiceInterface{
	@Autowired
	CenovnikRepository cenovnikRepository;
	
	@Autowired
	ProizvodRepository proizvodRepository;
	
	@Override
	public Cenovnik findByDatumIzdavanja(Date datumIzdavanja) {
		return cenovnikRepository.findByDatumIzdavanja(datumIzdavanja);
	}

	@Override
	public Cenovnik findOne(Integer id) {
		return cenovnikRepository.getOne(id);
	}

	@Override
	public List<Cenovnik> findAll() {
		return cenovnikRepository.findAll();
	}

	@Override
	public void save(CenovnikDTO cenovnikDTO) {
		Cenovnik cenovnik = new Cenovnik();
		cenovnik.setDatumIzdavanja(cenovnikDTO.getDatumIzdavanja());
		Set<StavkaCenovnika> stavkeCenovnika = new HashSet<StavkaCenovnika>();
		for(StavkaCenovnikaDTO stavkaCenovnikaDTO : cenovnikDTO.getStavkeCenovnika()) {
			StavkaCenovnika stavka = new StavkaCenovnika();
			stavka.setProizvod(proizvodRepository.getOne(stavkaCenovnikaDTO.getProizvod().getId()));
			stavka.setCena(stavkaCenovnikaDTO.getCena());
			stavka.setCenovnik(cenovnik);
			stavkeCenovnika.add(stavka);
		}
		cenovnik.setStavkeCenovnika(stavkeCenovnika);
		cenovnikRepository.save(cenovnik);
	}

	@Override
	public void remove(Integer id) {
		cenovnikRepository.deleteById(id);
		
	}

	@Override
	public List<CenovnikDTO> findAllByOrderByDatumIzdavanjaDesc() {
		List<CenovnikDTO> cenovniciDTO = new ArrayList<CenovnikDTO>();
		for(Cenovnik c : cenovnikRepository.findAllByOrderByDatumIzdavanjaDesc()) {
			cenovniciDTO.add(new CenovnikDTO(c));
		}
		return cenovniciDTO;
	}

}
