package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.GrupaProizvodaDTO;
import com.example.poslovnaInformatikaFTN.dto.PDVKategorijaDTO;
import com.example.poslovnaInformatikaFTN.dto.PDVStopaDTO;
import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.PDVKategorija;
import com.example.poslovnaInformatikaFTN.entity.PDVStopa;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.repository.GrupaProizvodaRepository;
import com.example.poslovnaInformatikaFTN.repository.PDVKategorijaRepository;
import com.example.poslovnaInformatikaFTN.repository.PDVStopaRepository;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;

@Transactional
@Service
public class PDVKategorijaService implements PDVKategorijaServiceInterface {
	
	@Autowired
	PDVKategorijaRepository pDVKategorijaRepository;
	
	@Autowired
	PDVStopaRepository pDVStopaRepository;
	
	@Autowired
	GrupaProizvodaRepository grupaProizvodaRepository;
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public PDVKategorija findOne(Integer id) {
		return pDVKategorijaRepository.getOne(id);
	}
	@Override
	public List<PDVKategorijaDTO> findAll() {
		List<PDVKategorijaDTO> pdvKategorijeDTO = new ArrayList<PDVKategorijaDTO>();
		for(PDVKategorija pdv : pDVKategorijaRepository.findAll()) {
			pdvKategorijeDTO.add(new PDVKategorijaDTO(pdv));
		}
		return pdvKategorijeDTO;
	}
	@Override
	public void save(PDVKategorijaDTO pdvKategorijaDTO) {
		PDVKategorija pdvKategorija = new PDVKategorija();
		pdvKategorija.setNaziv(pdvKategorijaDTO.getNaziv());
		Set<PDVStopa> stopePDV = new HashSet<PDVStopa>();
		for(PDVStopaDTO pdvStopaDTO : pdvKategorijaDTO.getStopePDV()) {
			PDVStopa pdvStopa = null;
			if(pdvStopaDTO.getId() != null) {
				pdvStopa = pDVStopaRepository.getOne(pdvStopaDTO.getId());
			}
			if(pdvStopa == null) {
				pdvStopa = new PDVStopa();
			}
			pdvStopa.setDatumPocetkaVazenja(pdvStopaDTO.getDatumPocetkaVazenja());
			pdvStopa.setAktivna(pdvStopaDTO.isAktivna());
			pdvStopa.setProcenat(pdvStopaDTO.getProcenat());
			pdvStopa.setPdvKategorija(pdvKategorija);
			stopePDV.add(pdvStopa);
		}
		pdvKategorija.setStopePDV(stopePDV);
		Set<GrupaProizvoda> grupeProizvoda = new HashSet<GrupaProizvoda>();
		for(GrupaProizvodaDTO grupaProizvodaDTO : pdvKategorijaDTO.getGrupeProizvoda()) {
			GrupaProizvoda grupa = null;
			if(grupaProizvodaDTO.getId() != null) {
				grupa = grupaProizvodaRepository.getOne(grupaProizvodaDTO.getId());
			}
			if(grupa == null) {
				grupa = new GrupaProizvoda();
			}
			grupa.setNaziv(grupaProizvodaDTO.getNaziv());
			grupa.setPdvKategorija(pdvKategorija);
			grupa.setPreduzece(preduzeceRepository.getOne(1));
			Set<Proizvod> proizvodi = new HashSet<Proizvod>();
			for(ProizvodDTO proizvodDTO : grupaProizvodaDTO.getProizvodi()) {
				Proizvod proizvod = new Proizvod();
				proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
				proizvod.setNaziv(proizvodDTO.getNaziv());
				proizvod.setObrisan(false);
				proizvod.setOpis(proizvodDTO.getOpis());
				proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
				proizvod.setGrupa(grupa);
				
				proizvodi.add(proizvod);
			}
			grupa.setProizvodi(proizvodi);
			
			grupeProizvoda.add(grupa);
		}
		pdvKategorija.setGrupeProizvoda(grupeProizvoda);
		
		pDVKategorijaRepository.save(pdvKategorija);
	}
	
	@Override
	public void update(PDVKategorijaDTO pdvKategorijaDTO) {
		PDVKategorija pdvKategorija = pDVKategorijaRepository.getOne(pdvKategorijaDTO.getId());
		pdvKategorija.setNaziv(pdvKategorijaDTO.getNaziv());
		Set<PDVStopa> stopePDV = new HashSet<PDVStopa>();
		for(PDVStopaDTO pdvStopaDTO : pdvKategorijaDTO.getStopePDV()) {
			PDVStopa pdvStopa = null;
			if(pdvStopaDTO.getId() != null) {
				pdvStopa = pDVStopaRepository.getOne(pdvStopaDTO.getId());
			}
			if(pdvStopa == null) {
				pdvStopa = new PDVStopa();
			}
			pdvStopa.setDatumPocetkaVazenja(pdvStopaDTO.getDatumPocetkaVazenja());
			pdvStopa.setAktivna(pdvStopaDTO.isAktivna());
			pdvStopa.setProcenat(pdvStopaDTO.getProcenat());
			pdvStopa.setPdvKategorija(pdvKategorija);
			stopePDV.add(pdvStopa);
		}
		pdvKategorija.setStopePDV(stopePDV);
		Set<GrupaProizvoda> grupeProizvoda = new HashSet<GrupaProizvoda>();
		for(GrupaProizvodaDTO grupaProizvodaDTO : pdvKategorijaDTO.getGrupeProizvoda()) {
			GrupaProizvoda grupa = null;
			if(grupaProizvodaDTO.getId() != null) {
				grupa = grupaProizvodaRepository.getOne(grupaProizvodaDTO.getId());
			}
			if(grupa == null) {
				grupa = new GrupaProizvoda();
			}
			grupa.setNaziv(grupaProizvodaDTO.getNaziv());
			grupa.setPdvKategorija(pdvKategorija);
			grupa.setPreduzece(preduzeceRepository.getOne(1));
			Set<Proizvod> proizvodi = new HashSet<Proizvod>();
			for(ProizvodDTO proizvodDTO : grupaProizvodaDTO.getProizvodi()) {
				Proizvod proizvod = new Proizvod();
				proizvod.setJedinicaMere(proizvodDTO.getJedinicaMere());
				proizvod.setNaziv(proizvodDTO.getNaziv());
				proizvod.setObrisan(false);
				proizvod.setOpis(proizvodDTO.getOpis());
				proizvod.setTipProizvoda(proizvodDTO.getTipProizvoda());
				proizvod.setGrupa(grupa);
				
				proizvodi.add(proizvod);
			}
			grupa.setProizvodi(proizvodi);
			
			grupeProizvoda.add(grupa);
		}
		pdvKategorija.setGrupeProizvoda(grupeProizvoda);
		
		pDVKategorijaRepository.save(pdvKategorija);
	}
	
	@Override
	public void remove(Integer id) {
		pDVKategorijaRepository.deleteById(id);
	}

}
