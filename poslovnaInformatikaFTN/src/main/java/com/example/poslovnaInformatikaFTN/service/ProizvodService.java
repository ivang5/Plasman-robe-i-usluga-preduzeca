package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.repository.GrupaProizvodaRepository;
import com.example.poslovnaInformatikaFTN.repository.ProizvodRepository;

@Transactional
@Service
public class ProizvodService implements ProizvodServiceInterface {
	
	@Autowired
	ProizvodRepository proizvodRepository;
	
	@Autowired
	GrupaProizvodaRepository grupaProizvodaRepository;
	
	@Override
	public Proizvod findOne(Integer id) {
		return proizvodRepository.getOne(id);
	}
	@Override
	public List<ProizvodDTO> findAll() {
		List<ProizvodDTO> proizvodiDTO = new ArrayList<ProizvodDTO>();
		for(Proizvod p : proizvodRepository.findAll()) {
			if(!p.isObrisan())
				proizvodiDTO.add(new ProizvodDTO(p));
		}
		return proizvodiDTO;
	}
	@Override
	public void save(ProizvodDTO proizvodDTO) {
		Proizvod proizvod = new Proizvod();
		proizvod.setNaziv(proizvodDTO.getNaziv());
		proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
		proizvod.setObrisan(false);
		proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
		if(proizvodDTO.getOpis() != null)
			proizvod.setOpis(proizvodDTO.getOpis());
		proizvod.setGrupa(grupaProizvodaRepository.getOne(proizvodDTO.getIdGrupe()));
		
		proizvodRepository.save(proizvod);
	}
	
	@Override
	public void update(ProizvodDTO proizvodDTO) {
		Proizvod proizvod = proizvodRepository.getOne(proizvodDTO.getId());
		proizvod.setNaziv(proizvodDTO.getNaziv());
		proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
		proizvod.setObrisan(false);
		proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
		if(proizvodDTO.getOpis() != null)
			proizvod.setOpis(proizvodDTO.getOpis());
		proizvod.setGrupa(grupaProizvodaRepository.getOne(proizvodDTO.getIdGrupe()));
		
		proizvodRepository.save(proizvod);
	}
	
	@Override
	public void remove(Integer id) {
		Proizvod proizvod = proizvodRepository.getOne(id);
		if(proizvod != null) {
			proizvod.setObrisan(true);
			proizvodRepository.save(proizvod);
		}
	}

}
