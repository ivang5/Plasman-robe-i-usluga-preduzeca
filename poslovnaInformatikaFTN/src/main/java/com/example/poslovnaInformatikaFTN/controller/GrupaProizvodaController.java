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

import com.example.poslovnaInformatikaFTN.dto.GrupaProizvodaDTO;
import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaFaktureDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.service.GrupaProizvodaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PDVKategorijaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PreduzeceServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ProizvodServiceInterface;

@RestController
@RequestMapping(value="api/grupeProizvoda")
public class GrupaProizvodaController {
	
	@Autowired
	private GrupaProizvodaServiceInterface grupaProizvodaService;
	
	@Autowired
	private PDVKategorijaServiceInterface pdvKategorijaService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<GrupaProizvodaDTO>getGrupaProizvodaById(@PathVariable("id") int id){
		GrupaProizvoda grupaProizvoda = grupaProizvodaService.findOne(id);
		
		if(grupaProizvoda == null){
			return new ResponseEntity<GrupaProizvodaDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GrupaProizvodaDTO>(new GrupaProizvodaDTO(grupaProizvoda), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<GrupaProizvodaDTO>>getAllGrupeProizvoda(){	
		return new ResponseEntity<List<GrupaProizvodaDTO>>(grupaProizvodaService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createGrupaProizvoda(@RequestBody GrupaProizvodaDTO grupaProizvodaDTO){
		if(grupaProizvodaDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(grupaProizvodaDTO.getProizvodi() == null || grupaProizvodaDTO.getProizvodi().isEmpty())
			return new ResponseEntity<String>("Zasto si napravio grupu bez proizvoda!", HttpStatus.BAD_REQUEST);
		if(grupaProizvodaDTO.getIdPdvKategorije() == null || grupaProizvodaDTO.getIdPdvKategorije() < 0 || pdvKategorijaService.findOne(grupaProizvodaDTO.getIdPdvKategorije()) == null)
			return new ResponseEntity<String>("Nisi dobro uneo pdv kategoriju!", HttpStatus.BAD_REQUEST);
			
		try {
			grupaProizvodaService.save(grupaProizvodaDTO);

		}catch (Exception e) {
			return new ResponseEntity<String>("Proizvodi nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateGrupaProizvoda(@RequestBody GrupaProizvodaDTO grupaProizvodaDTO){
		if(grupaProizvodaDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(grupaProizvodaDTO.getProizvodi() == null || grupaProizvodaDTO.getProizvodi().isEmpty())
			return new ResponseEntity<String>("Moras imati proizvode u grupi!", HttpStatus.BAD_REQUEST);
		if(grupaProizvodaDTO.getIdPdvKategorije() == null || grupaProizvodaDTO.getIdPdvKategorije() < 0 || pdvKategorijaService.findOne(grupaProizvodaDTO.getIdPdvKategorije()) == null)
			return new ResponseEntity<String>("Nisi dobro uneo pdv kategoriju!", HttpStatus.BAD_REQUEST);
		
		try {
			grupaProizvodaService.update(grupaProizvodaDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Proizvodi nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteGrupaProizvoda(@PathVariable Integer id){
		if(grupaProizvodaService.remove(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
