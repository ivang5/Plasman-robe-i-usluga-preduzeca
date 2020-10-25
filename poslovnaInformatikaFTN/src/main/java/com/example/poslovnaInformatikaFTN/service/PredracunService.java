package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.PredracunDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaPredracunaDTO;
import com.example.poslovnaInformatikaFTN.entity.Predracun;
import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;
import com.example.poslovnaInformatikaFTN.repository.PoslovnaGodinaRepository;
import com.example.poslovnaInformatikaFTN.repository.PoslovniPartnerRepository;
import com.example.poslovnaInformatikaFTN.repository.PredracunRepository;
import com.example.poslovnaInformatikaFTN.repository.ProizvodRepository;
import com.example.poslovnaInformatikaFTN.repository.ZaposleniRepository;

@Transactional
@Service
public class PredracunService implements PredracunServiceInterface {
	
	@Autowired
	PredracunRepository predracunRepository;
	
	@Autowired
	ZaposleniRepository zaposleniRepository;
	
	@Autowired
	ProizvodRepository proizvodRepository;
	
	@Autowired
	PoslovnaGodinaRepository poslovnaGodinaRepository;
	
	@Autowired
	PoslovniPartnerRepository poslovniPartnerRepository;
	
	@Override
	public Predracun findOne(Integer id) {
		return predracunRepository.getOne(id);
	}
	
	@Override
	public List<PredracunDTO> findAll() {
		List<PredracunDTO> predracuniDTO = new ArrayList<PredracunDTO>();
		for(Predracun p : predracunRepository.findAll()) {
			predracuniDTO.add(new PredracunDTO(p));
		}
		return predracuniDTO;
	}
	@Override
	public void save(PredracunDTO predracunDTO) {
		Predracun predracun = new Predracun();
		predracun.setIzdavalac(zaposleniRepository.getOne(predracunDTO.getIdIzdavaoca()));
		Set<StavkaPredracuna> stavkePredracuna = new HashSet<StavkaPredracuna>();
		for(StavkaPredracunaDTO stavkaPredracunaDTO : predracunDTO.getStavkePredracuna()) {
			StavkaPredracuna stavka = new StavkaPredracuna();
			stavka.setProizvod(proizvodRepository.getOne(stavkaPredracunaDTO.getProizvod().getId()));
			stavka.setCena(stavkaPredracunaDTO.getCena());
			stavka.setIznos(stavkaPredracunaDTO.getIznos());
			stavka.setKolicina(stavkaPredracunaDTO.getKolicina());
			stavka.setPdv(stavkaPredracunaDTO.getPdv());
			stavka.setRabat(stavkaPredracunaDTO.getRabat());
			stavka.setPredracun(predracun);
			stavkePredracuna.add(stavka);
		}
		predracun.setStavkePredracuna(stavkePredracuna);
		predracun.setRokIsporuke(predracunDTO.getRokIsporuke());
		predracun.setRokPlacanja(predracunDTO.getRokPlacanja());
		predracun.setBrojRacuna(predracunDTO.getBrojRacuna());
		predracun.setIznos(predracunDTO.getIznos());
		predracun.setPdv(predracunDTO.getPdv());
		predracun.setUkupanRabat(predracunDTO.getUkupanRabat());
		predracun.setKupac(poslovniPartnerRepository.getOne(predracunDTO.getPoslovnaGodina()));
		predracun.setGodina(poslovnaGodinaRepository.getOne(predracunDTO.getKupac().getId()));
		
		predracunRepository.save(predracun);
	}
	@Override
	public void remove(Integer id) {
		predracunRepository.deleteById(id);
	}

}
