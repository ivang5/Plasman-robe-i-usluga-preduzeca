package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.GrupaProizvodaDTO;
import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.repository.GrupaProizvodaRepository;
import com.example.poslovnaInformatikaFTN.repository.PDVKategorijaRepository;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;
import com.example.poslovnaInformatikaFTN.repository.ProizvodRepository;

@Transactional
@Service
public class GrupaProizvodaService implements GrupaProizvodaServiceInterface {
	
	@Autowired
	GrupaProizvodaRepository grupaProizvodaRepository;
	
	@Autowired
	PDVKategorijaRepository pDVKategorijaRepository;
	
	@Autowired
	ProizvodRepository proizvodRepository;
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public GrupaProizvoda findOne(Integer id) {
		return grupaProizvodaRepository.getOne(id);
	}
	@Override
	public List<GrupaProizvodaDTO> findAll() {
		List<GrupaProizvodaDTO> grupeProizvodaDTO = new ArrayList<GrupaProizvodaDTO>();
		for(GrupaProizvoda gr : grupaProizvodaRepository.findAll()) {
			if(!gr.isObrisan())
				grupeProizvodaDTO.add(new GrupaProizvodaDTO(gr));
		}
		return grupeProizvodaDTO;
	}
	@Override
	public void save(GrupaProizvodaDTO grupaProizvodaDTO) {
		GrupaProizvoda grupaProizvoda = new GrupaProizvoda();
		grupaProizvoda.setNaziv(grupaProizvodaDTO.getNaziv());
		grupaProizvoda.setPdvKategorija(pDVKategorijaRepository.getOne(grupaProizvodaDTO.getIdPdvKategorije()));
		Set<Proizvod> proizvodi = new HashSet<Proizvod>();
		for(ProizvodDTO proizvodDTO : grupaProizvodaDTO.getProizvodi()) {
			Proizvod proizvod = null;
			if(proizvodDTO.getId() != null) {
				proizvod = proizvodRepository.getOne(proizvodDTO.getId());
			}
			if(proizvod == null) {
				proizvod = new Proizvod();
			}
			proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
			proizvod.setNaziv(proizvodDTO.getNaziv());
			proizvod.setObrisan(false);
			proizvod.setOpis(proizvodDTO.getOpis());
			proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
			proizvod.setGrupa(grupaProizvoda);
			
			proizvodi.add(proizvod);
		}
		grupaProizvoda.setProizvodi(proizvodi);
		grupaProizvoda.setPreduzece(preduzeceRepository.getOne(1));
		grupaProizvodaRepository.save(grupaProizvoda);
	}
	
	@Override
	public void update(GrupaProizvodaDTO grupaProizvodaDTO) {
		GrupaProizvoda grupaProizvoda = grupaProizvodaRepository.getOne(grupaProizvodaDTO.getId());
		grupaProizvoda.setNaziv(grupaProizvodaDTO.getNaziv());
		grupaProizvoda.setPdvKategorija(pDVKategorijaRepository.getOne(grupaProizvodaDTO.getIdPdvKategorije()));
		Set<Proizvod> proizvodi = new HashSet<Proizvod>();
		for(ProizvodDTO proizvodDTO : grupaProizvodaDTO.getProizvodi()) {
			Proizvod proizvod = null;
			if(proizvodDTO.getId() != null) {
				proizvod = proizvodRepository.getOne(proizvodDTO.getId());
			}
			if(proizvod == null) {
				proizvod = new Proizvod();
			}
			proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
			proizvod.setNaziv(proizvodDTO.getNaziv());
			proizvod.setObrisan(false);
			proizvod.setOpis(proizvodDTO.getOpis());
			proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
			proizvod.setGrupa(grupaProizvoda);
			
			proizvodi.add(proizvod);
		}
		grupaProizvoda.setProizvodi(proizvodi);
		grupaProizvoda.setPreduzece(preduzeceRepository.getOne(1));
		grupaProizvodaRepository.save(grupaProizvoda);
	}
	
	@Override
	public boolean remove(Integer id) {
		GrupaProizvoda grupaProizvoda = grupaProizvodaRepository.getOne(id);
		if(grupaProizvoda != null) {
			grupaProizvoda.setObrisan(true);
			for(Proizvod p : grupaProizvoda.getProizvodi()) {
				p.setObrisan(true);
			}
			grupaProizvodaRepository.save(grupaProizvoda);
			return true;
		}else {
			return false;
		}
		//grupaProizvodaRepository.deleteById(id);
	}

}
