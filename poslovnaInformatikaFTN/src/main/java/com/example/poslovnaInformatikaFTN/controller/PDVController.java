package com.example.poslovnaInformatikaFTN.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.GrupaProizvodaDTO;
import com.example.poslovnaInformatikaFTN.dto.PDVKategorijaDTO;
import com.example.poslovnaInformatikaFTN.dto.PDVStopaDTO;
import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.PDVKategorija;
import com.example.poslovnaInformatikaFTN.entity.PDVStopa;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.service.GrupaProizvodaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PDVKategorijaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PDVStopaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PreduzeceServiceInterface;

@RestController
@RequestMapping(value="api/pdvKategorije")
public class PDVController {
	
	@Autowired
	private PDVKategorijaServiceInterface pdvKategorijaService;
	
	@Autowired
	private PDVStopaServiceInterface pdvStopaService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<PDVKategorijaDTO>getPDVKategorijaById(@PathVariable("id") int id){
		PDVKategorija pdvKategorija = pdvKategorijaService.findOne(id);
		
		if(pdvKategorija == null){
			return new ResponseEntity<PDVKategorijaDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PDVKategorijaDTO>(new PDVKategorijaDTO(pdvKategorija), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PDVKategorijaDTO>>getAllPDVKategorije(){
		return new ResponseEntity<List<PDVKategorijaDTO>>(pdvKategorijaService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createPdvKategorija(@RequestBody PDVKategorijaDTO pdvKategorijaDTO){
		if(pdvKategorijaDTO.getNaziv() == null || pdvKategorijaDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Naziv nije unet", HttpStatus.BAD_REQUEST);
		if(pdvKategorijaDTO.getStopePDV() == null || pdvKategorijaDTO.getStopePDV().isEmpty())
			return new ResponseEntity<String>("Moras imati stope pdv-a!", HttpStatus.BAD_REQUEST);
		if(pdvKategorijaDTO.getGrupeProizvoda() == null || pdvKategorijaDTO.getGrupeProizvoda().isEmpty())
			return new ResponseEntity<String>("Moras imati grupe proizvoda!", HttpStatus.BAD_REQUEST);
		
		PDVKategorija pdvKategorija = new PDVKategorija();
		pdvKategorija.setNaziv(pdvKategorijaDTO.getNaziv());
		try {
			pdvKategorijaService.save(pdvKategorijaDTO);	
		}catch (Exception e) {
			return new ResponseEntity<String>("Stope pdv-a nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<?> updatePdvKategorija(@RequestBody PDVKategorijaDTO pdvKategorijaDTO){
		if(pdvKategorijaDTO.getNaziv() == null || pdvKategorijaDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Naziv nije unet", HttpStatus.BAD_REQUEST);
		if(pdvKategorijaDTO.getStopePDV() == null || pdvKategorijaDTO.getStopePDV().isEmpty())
			return new ResponseEntity<String>("Moras imati stope pdv-a!", HttpStatus.BAD_REQUEST);
		if(pdvKategorijaDTO.getGrupeProizvoda() == null || pdvKategorijaDTO.getGrupeProizvoda().isEmpty())
			return new ResponseEntity<String>("Moras imati grupe proizvoda!", HttpStatus.BAD_REQUEST);
		
		try {
			pdvKategorijaService.update(pdvKategorijaDTO);
			
		}catch (Exception e) {
			return new ResponseEntity<String>("Stope pdv-a nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/stope")
	public ResponseEntity<List<PDVStopaDTO>>getAllPDVStope(){
		return new ResponseEntity<List<PDVStopaDTO>>(pdvStopaService.findAll(), HttpStatus.OK);
	}
	
}
