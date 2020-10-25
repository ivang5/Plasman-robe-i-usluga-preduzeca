package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.PoslovniPartnerDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.repository.PoslovniPartnerRepository;
import com.example.poslovnaInformatikaFTN.repository.RacunUBanciRepository;

@Transactional
@Service
public class PoslovniPartnerService implements PoslovniPartnerServiceInterface {
	
	@Autowired
	PoslovniPartnerRepository poslovniPartnerRepository;
	
	@Autowired
	RacunUBanciRepository racunUBanciRepository;
	
	@Override
	public PoslovniPartner findOne(Integer id) {
		return poslovniPartnerRepository.getOne(id);
	}
	@Override
	public List<PoslovniPartnerDTO> findAll() {
		List<PoslovniPartnerDTO> poslovniPartneriDTO = new ArrayList<PoslovniPartnerDTO>();
		for(PoslovniPartner itPoslovniPartner : poslovniPartnerRepository.findAll()) {
			if(!itPoslovniPartner.isObrisan())
				poslovniPartneriDTO.add(new PoslovniPartnerDTO(itPoslovniPartner));
		}
		return poslovniPartneriDTO;
	}
	@Override
	public void save(PoslovniPartnerDTO poslovniPartnerDTO) {
		PoslovniPartner poslovniPartner = new PoslovniPartner();
		poslovniPartner.setAdresa(poslovniPartnerDTO.getAdresa());
		poslovniPartner.setEmail(poslovniPartnerDTO.getEmail());
		poslovniPartner.setNaziv(poslovniPartnerDTO.getNaziv());
		poslovniPartner.setPib(poslovniPartnerDTO.getPib());
		poslovniPartner.setTelefon(poslovniPartnerDTO.getTelefon());
		poslovniPartner.setVrstaPartnera(poslovniPartnerDTO.getVrstaPartnera());
		poslovniPartner.setObrisan(false);
		Set<RacunUBanci> racuniUBanci = new HashSet<RacunUBanci>();
		for(RacunUBanciDTO racunIt : poslovniPartnerDTO.getRacuniUBanci()){
			RacunUBanci racunUBanci = null;
			if(racunIt.getId() != null) {
				racunUBanci = racunUBanciRepository.getOne(racunIt.getId());
			}
			if(racunUBanci == null) {
				racunUBanci = new RacunUBanci();
			}
			racunUBanci.setBrojRacuna(racunIt.getBrojRacuna());
			racunUBanci.setNazivBanke(racunIt.getNazivBanke());
			racunUBanci.setObrisan(racunIt.isObrisan());
			racuniUBanci.add(racunUBanci);
		}
		poslovniPartner.setRacuniUBanci(racuniUBanci);
		
		poslovniPartnerRepository.save(poslovniPartner);
	}
	@Override
	public void update(PoslovniPartnerDTO poslovniPartnerDTO) {
		PoslovniPartner poslovniPartner = poslovniPartnerRepository.getOne(poslovniPartnerDTO.getId());
		poslovniPartner.setAdresa(poslovniPartnerDTO.getAdresa());
		poslovniPartner.setEmail(poslovniPartnerDTO.getEmail());
		poslovniPartner.setNaziv(poslovniPartnerDTO.getNaziv());
		poslovniPartner.setPib(poslovniPartnerDTO.getPib());
		poslovniPartner.setTelefon(poslovniPartnerDTO.getTelefon());
		poslovniPartner.setVrstaPartnera(poslovniPartnerDTO.getVrstaPartnera());
		poslovniPartner.setObrisan(false);
		Set<RacunUBanci> racuniUBanci = new HashSet<RacunUBanci>();
		for(RacunUBanciDTO racunIt : poslovniPartnerDTO.getRacuniUBanci()){
			RacunUBanci racunUBanci = null;
			if(racunIt.getId() != null) {
				racunUBanci = racunUBanciRepository.getOne(racunIt.getId());
			}
			if(racunUBanci == null) {
				racunUBanci = new RacunUBanci();
			}
			racunUBanci.setBrojRacuna(racunIt.getBrojRacuna());
			racunUBanci.setNazivBanke(racunIt.getNazivBanke());
			racunUBanci.setObrisan(racunIt.isObrisan());
			racuniUBanci.add(racunUBanci);
		}
		poslovniPartner.setRacuniUBanci(racuniUBanci);
		
		poslovniPartnerRepository.save(poslovniPartner);
	}
	@Override
	public void remove(Integer id) {
		PoslovniPartner poslovniPartner = poslovniPartnerRepository.getOne(id);
		if(poslovniPartner != null) {
			poslovniPartner.setObrisan(true);
			poslovniPartnerRepository.save(poslovniPartner);
		}		
	}
	@Override
	public void addRacun(RacunUBanciDTO racunUBanciDTO, Integer idPartnera) {
		PoslovniPartner poslovniPartner = poslovniPartnerRepository.getOne(idPartnera);
		Set<RacunUBanci> racuniUBanci = poslovniPartner.getRacuniUBanci();
		RacunUBanci racunUBanci = new RacunUBanci();
		racunUBanci.setBrojRacuna(racunUBanciDTO.getBrojRacuna());
		racunUBanci.setNazivBanke(racunUBanciDTO.getNazivBanke());
		racunUBanci.setObrisan(racunUBanciDTO.isObrisan());
		racuniUBanci.add(racunUBanci);
			
		poslovniPartner.setRacuniUBanci(racuniUBanci);
		
		poslovniPartnerRepository.save(poslovniPartner);
	}
	@Override
	public void deleteRacun(Integer idPartnera, Integer idRacuna) {
		PoslovniPartner poslovniPartner = poslovniPartnerRepository.getOne(idPartnera);
		RacunUBanci racunZaBrisanje = null;
		for(RacunUBanci r : poslovniPartner.getRacuniUBanci()) {
			if(r.getId() == idRacuna) {
				racunZaBrisanje = r;
			}
		}
		racunZaBrisanje.setObrisan(true);
		poslovniPartner = poslovniPartnerRepository.save(poslovniPartner);
		
	}

}
