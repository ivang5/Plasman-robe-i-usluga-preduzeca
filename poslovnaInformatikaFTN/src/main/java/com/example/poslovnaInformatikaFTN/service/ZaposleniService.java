package com.example.poslovnaInformatikaFTN.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.poslovnaInformatikaFTN.dto.ZaposleniDTO;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;
import com.example.poslovnaInformatikaFTN.repository.ZaposleniRepository;

@Transactional
@Service
public class ZaposleniService implements ZaposleniServiceInterface{
	
	@Autowired
	ZaposleniRepository zaposleniRepository;
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public Zaposleni findOne(Integer id) {
		return zaposleniRepository.getOne(id);
	}
	
	@Override
	public List<ZaposleniDTO> findAll() {
		List<ZaposleniDTO> zaposleniDTO = new ArrayList<ZaposleniDTO>();
		for(Zaposleni itZaposleni : zaposleniRepository.findAll()) {
			if(!itZaposleni.isObrisan()) {
				zaposleniDTO.add(new ZaposleniDTO(itZaposleni));
			}
		}
		return zaposleniDTO;
	}

	@Override
	public void save(ZaposleniDTO zaposleniDTO) {
		Zaposleni zaposleni = new Zaposleni();
		zaposleni.setIme(zaposleniDTO.getIme());
		zaposleni.setPrezime(zaposleniDTO.getPrezime());
		zaposleni.setAdresa(zaposleniDTO.getAdresa());
		zaposleni.setBrojTelefona(zaposleniDTO.getBrojTelefona());
		zaposleni.setUloga(zaposleniDTO.getUloga());
		zaposleni.setPreduzece(preduzeceRepository.getOne(1));
		zaposleni.setObrisan(false);
		
		zaposleniRepository.save(zaposleni);
	}
	
	@Override
	public void update(ZaposleniDTO zaposleniDTO) {
		Zaposleni zaposleni = zaposleniRepository.getOne(zaposleniDTO.getId());
		zaposleni.setIme(zaposleniDTO.getIme());
		zaposleni.setPrezime(zaposleniDTO.getPrezime());
		zaposleni.setAdresa(zaposleniDTO.getAdresa());
		zaposleni.setBrojTelefona(zaposleniDTO.getBrojTelefona());
		zaposleni.setUloga(zaposleniDTO.getUloga());
		zaposleni.setPreduzece(preduzeceRepository.getOne(1));
		zaposleni.setObrisan(false);
		
		zaposleniRepository.save(zaposleni);
		
	}

	@Override
	public void remove(Integer id) {
		Zaposleni zaposleni = zaposleniRepository.getOne(id);
		if(zaposleni != null) {
			zaposleni.setObrisan(true);
			zaposleniRepository.save(zaposleni);
		}
	}

}
