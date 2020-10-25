package com.example.poslovnaInformatikaFTN.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.ZaposleniDTO;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.PreduzeceServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ZaposleniServiceInterface;

@RestController
@RequestMapping(value="api/zaposleni")
public class ZaposleniController {
	
	@Autowired
	private ZaposleniServiceInterface zaposleniService;

	
	@GetMapping
	public ResponseEntity<List<ZaposleniDTO>>getAllZaposleni(){
		return new ResponseEntity<List<ZaposleniDTO>>(zaposleniService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ZaposleniDTO>getZaposleni(@PathVariable Integer id){
		Zaposleni zaposleni = zaposleniService.findOne(id);
		if(zaposleni == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>(new ZaposleniDTO(zaposleni), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> saveZaposleni(@RequestBody ZaposleniDTO zaposleniDTO){			
		if(zaposleniDTO.getAdresa().isEmpty()) {
			return new ResponseEntity<String>("Polje Adresa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getBrojTelefona().isEmpty()) {
			return new ResponseEntity<String>("Polje BrojTelefona ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getIme().isEmpty()) {
			return new ResponseEntity<String>("Polje Ime ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getPrezime().isEmpty()) {
			return new ResponseEntity<String>("Polje Prezime ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getUloga( )== null) {
			return new ResponseEntity<String>("Polje Uloga ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		
		try {
			zaposleniService.save(zaposleniDTO);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev, nešto nije u redu!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);	
	}
	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<?> updateZaposleni(@RequestBody ZaposleniDTO zaposleniDTO){
		if(zaposleniService.findOne(zaposleniDTO.getId()) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getId()== null) {
			return new ResponseEntity<String>("Polje ID ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getAdresa().isEmpty()) {
			return new ResponseEntity<String>("Polje Adresa ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getBrojTelefona().isEmpty()) {
			return new ResponseEntity<String>("Polje BrojTelefona ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getIme().isEmpty()) {
			return new ResponseEntity<String>("Polje Ime ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getPrezime().isEmpty()) {
			return new ResponseEntity<String>("Polje Prezime ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		if(zaposleniDTO.getUloga( )== null) {
			return new ResponseEntity<String>("Polje Uloga ne može biti prazno!", HttpStatus.BAD_REQUEST);
		}
		
		try {
			zaposleniService.update(zaposleniDTO);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev, nešto nije u redu!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteZaposleni(@PathVariable Integer id){
		Zaposleni zaposleni = zaposleniService.findOne(id);
		if(zaposleni != null) {
			zaposleniService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	


}
