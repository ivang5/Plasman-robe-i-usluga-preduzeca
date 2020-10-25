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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.PoslovniPartnerDTO;
import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunUBanciDTO;
import com.example.poslovnaInformatikaFTN.dto.ZaposleniDTO;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.PoslovniPartnerServiceInterface;
import com.example.poslovnaInformatikaFTN.service.RacunUBanciServiceInterface;

@RestController
@RequestMapping(value="api/poslovniPartneri")
public class PoslovniPartnerController {
	
	@Autowired
	private PoslovniPartnerServiceInterface poslovniPartnerService;
	
	@Autowired
	private RacunUBanciServiceInterface racunServis;
	
	@GetMapping
	public ResponseEntity<List<PoslovniPartnerDTO>>getAllPoslovniPartneri(){
		return new ResponseEntity<List<PoslovniPartnerDTO>>(poslovniPartnerService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<PoslovniPartnerDTO>getPoslovniPartner(@PathVariable Integer id){
		PoslovniPartner poslovniPartner = poslovniPartnerService.findOne(id);
		if(poslovniPartner == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new PoslovniPartnerDTO(poslovniPartner), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> savePoslovniPartner(@RequestBody PoslovniPartnerDTO poslovniPartnerDTO){	
		if(poslovniPartnerDTO.getAdresa().isEmpty())
			return new ResponseEntity<String>("Polje Adresa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getEmail().isEmpty())
			return new ResponseEntity<String>("Polje Email ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje Naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getPib() == 0)
			return new ResponseEntity<String>("Polje Pib ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getTelefon().isEmpty())
			return new ResponseEntity<String>("Polje Telefon ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getVrstaPartnera() == null)
			return new ResponseEntity<String>("Polje VrstaPartnera ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			poslovniPartnerService.save(poslovniPartnerDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Racuni u banci nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<?> updatePoslovniPartner(@RequestBody PoslovniPartnerDTO poslovniPartnerDTO){
		if(poslovniPartnerDTO.getId()== null)
			return new ResponseEntity<String>("Polje ID ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getAdresa().isEmpty())
			return new ResponseEntity<String>("Polje Adresa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getEmail().isEmpty())
			return new ResponseEntity<String>("Polje Email ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje Naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getPib() == 0)
			return new ResponseEntity<String>("Polje Pib ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getTelefon().isEmpty())
			return new ResponseEntity<String>("Polje Telefon ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerDTO.getVrstaPartnera() == null)
			return new ResponseEntity<String>("Polje VrstaPartnera ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		if(poslovniPartnerService.findOne(poslovniPartnerDTO.getId()) == null)
			return new ResponseEntity<String>("Poslovni partner ne postoji u bazi!", HttpStatus.BAD_REQUEST);
		
		try {
			poslovniPartnerService.update(poslovniPartnerDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Racuni u banci nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value="/addRacun/{idPartnera}")
	public ResponseEntity<?> addRacunPoslovnogPartnera(@RequestBody RacunUBanciDTO racunUBanciDTO, @PathVariable Integer idPartnera){
		if(racunUBanciDTO == null)
			return new ResponseEntity<String>("Polje racun u banci ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(racunUBanciDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("Polje broj računa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(racunUBanciDTO.getNazivBanke().isEmpty())
			return new ResponseEntity<String>("Polje naziv banke ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			poslovniPartnerService.addRacun(racunUBanciDTO, idPartnera);
		}catch (Exception e) {
			return new ResponseEntity<String>("Racuni u banci nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping(value="/deleteRacun/{idPartnera}/{idRacuna}")
	public ResponseEntity<?> deleteRacunPoslovnogPartnera(@PathVariable Integer idPartnera, @PathVariable Integer idRacuna){
		if(idPartnera == null)
			return new ResponseEntity<String>("Polje id partnera ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(idRacuna == null)
			return new ResponseEntity<String>("Polje id racuna ne može biti prazno!", HttpStatus.BAD_REQUEST);
		
		try {
			poslovniPartnerService.deleteRacun(idPartnera, idRacuna);
		} catch (Exception e) {
			return new ResponseEntity<String>("Polje id ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delletePoslovniPartner(@PathVariable Integer id){
		PoslovniPartner poslovniPartner = poslovniPartnerService.findOne(id);
		if(poslovniPartner != null) {
			poslovniPartnerService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
