package com.example.poslovnaInformatikaFTN.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.CenovnikDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaCenovnikaDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaFaktureDTO;
import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.service.CenovnikServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ProizvodServiceInterface;

@RestController
@RequestMapping(value="api/cenovnik")
public class CenovnikController {
	
	@Autowired
	private CenovnikServiceInterface cenovnikService;
	
	@Autowired
	private ProizvodServiceInterface proizvodService;
	
	@GetMapping
	public ResponseEntity<List<CenovnikDTO>>getAllCenovnici(){
		return new ResponseEntity<List<CenovnikDTO>>(cenovnikService.findAllByOrderByDatumIzdavanjaDesc(), HttpStatus.OK);
	}
	
	@GetMapping(value="/newest")
	public ResponseEntity<CenovnikDTO>getNewestCenovnik(){
		return new ResponseEntity<CenovnikDTO>(cenovnikService.findAllByOrderByDatumIzdavanjaDesc().get(0), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> addCenovnik(@RequestBody CenovnikDTO cenovnikDTO){
		if(cenovnikDTO.getDatumIzdavanja() == null)
			return new ResponseEntity<String>("Nisi dobro uneo datum!", HttpStatus.BAD_REQUEST);
		if(cenovnikDTO.getStavkeCenovnika().isEmpty())
			return new ResponseEntity<String>("Nisi dobro uneo stavke!", HttpStatus.BAD_REQUEST);

		try {
			cenovnikService.save(cenovnikDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Stavke cenovnika nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
