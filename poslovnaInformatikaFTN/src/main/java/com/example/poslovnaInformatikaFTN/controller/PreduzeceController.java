package com.example.poslovnaInformatikaFTN.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.PoslovnaGodinaDTO;
import com.example.poslovnaInformatikaFTN.dto.PreduzeceDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.dto.ZaposleniDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.PoslovnaGodinaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PreduzeceServiceInterface;
import com.example.poslovnaInformatikaFTN.service.RacunUBanciServiceInterface;

@RestController
@RequestMapping(value="api/preduzece")
public class PreduzeceController {
	
	@Autowired
	private PreduzeceServiceInterface preduzeceService;
	
	@Autowired
	private RacunUBanciServiceInterface racunServis;
	
	@Autowired
	private PoslovnaGodinaServiceInterface poslovnaGodinaInterface;
	
	@GetMapping
	public ResponseEntity<PreduzeceDTO>getPreduzece(){
		List<Preduzece> preduzeca = preduzeceService.findAll();
		
		return new ResponseEntity<PreduzeceDTO>(new PreduzeceDTO(preduzeca.get(0)), HttpStatus.OK);	
	}
	
	@PutMapping
	public ResponseEntity<?> updatePreduzece(@RequestBody PreduzeceDTO preduzeceDTO){
		if(preduzeceDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(preduzeceDTO.getId() == null || preduzeceService.findOne(preduzeceDTO.getId()) == null)
			return new ResponseEntity<String>("Preduzece ne postoji u bazi!", HttpStatus.BAD_REQUEST);
		if(preduzeceDTO.getPib() == 0 || preduzeceDTO.getPib() < 0)
			return new ResponseEntity<String>("Polje pib ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(preduzeceDTO.getTelefon().isEmpty())
			return new ResponseEntity<String>("Polje telefon ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			preduzeceService.update(preduzeceDTO);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@PostMapping(value="/addRacun")
	public ResponseEntity<?> addRacunPreduzeca(@RequestBody RacunUBanciDTO racunUBanciDTO){
		if(racunUBanciDTO == null)
			return new ResponseEntity<String>("Polje racun u banci ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(racunUBanciDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("Polje broj računa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(racunUBanciDTO.getNazivBanke().isEmpty())
			return new ResponseEntity<String>("Polje naziv banke ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			preduzeceService.addRacun(racunUBanciDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Racuni u banci nisu u redu", HttpStatus.BAD_REQUEST);
		}

		
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping(value="/deleteRacun/{id}")
	public ResponseEntity<?> deleteRacunPreduzeca(@PathVariable Integer id){
		if(id == null)
			return new ResponseEntity<String>("Polje id ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			preduzeceService.deleteRacun(id);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@GetMapping(value="/poslovneGodine")
	public ResponseEntity<List<PoslovnaGodinaDTO>>getPoslovneGodine(){
		return new ResponseEntity<List<PoslovnaGodinaDTO>>(poslovnaGodinaInterface.findAll(), HttpStatus.OK);	
	}
	
	@GetMapping(value="/poslovneGodine/{godina}")
	public ResponseEntity<?>getPoslovnaGodina(@PathVariable Integer godina){
		if(godina == null)
			return new ResponseEntity<String>("Moraš poslati broj godine", HttpStatus.BAD_REQUEST);
		
		PoslovnaGodinaDTO poslovnaGodinaDTO = poslovnaGodinaInterface.findOneByGodina(godina);

		if(poslovnaGodinaDTO == null)
			return new ResponseEntity<String>("Nije pronadjena trazena godina", HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<PoslovnaGodinaDTO>(poslovnaGodinaDTO, HttpStatus.OK);
		
	}

}
