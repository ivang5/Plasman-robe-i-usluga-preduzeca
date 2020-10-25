package com.example.poslovnaInformatikaFTN.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.ProizvodDTO;
import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.GrupaProizvodaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ProizvodServiceInterface;

@RestController
@RequestMapping(value="api/proizvodi")
public class ProizvodController {
	
	@Autowired
	private ProizvodServiceInterface proizvodService;
	
	@Autowired
	private GrupaProizvodaServiceInterface grupaProizvodaService;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<ProizvodDTO>>getAllProizvod(){
		return new ResponseEntity<List<ProizvodDTO>>(proizvodService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createProizvod(@RequestBody ProizvodDTO proizvodDTO){
		if(proizvodDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getJedinicaMere().isEmpty())
			return new ResponseEntity<String>("Polje jedinica mere ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getTipProizvoda() == null)
			return new ResponseEntity<String>("Polje tip proizvoda ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getIdGrupe() == null || proizvodDTO.getIdGrupe() < 0 || grupaProizvodaService.findOne(proizvodDTO.getIdGrupe()) == null)
			return new ResponseEntity<String>("Pogresan unos polja grupa proizvoda!", HttpStatus.BAD_REQUEST);
			
		try {
			proizvodService.save(proizvodDTO);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev, nešto nije u redu", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateProizvod(@RequestBody ProizvodDTO proizvodDTO){
		if(proizvodDTO.getNaziv().isEmpty())
			return new ResponseEntity<String>("Polje naziv ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getJedinicaMere().isEmpty())
			return new ResponseEntity<String>("Polje jedinica mere ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getTipProizvoda() == null)
			return new ResponseEntity<String>("Polje tip proizvoda ne može biti prazno!", HttpStatus.BAD_REQUEST);
		if(proizvodDTO.getIdGrupe() == null || proizvodDTO.getIdGrupe() < 0 || grupaProizvodaService.findOne(proizvodDTO.getIdGrupe()) == null)
			return new ResponseEntity<String>("Pogresan unos polja grupa proizvoda!", HttpStatus.BAD_REQUEST);
		if(proizvodService.findOne(proizvodDTO.getId()) == null)
			return new ResponseEntity<String>("Proizvod ne postoji u bazi!", HttpStatus.BAD_REQUEST);
		
		try {
			proizvodService.update(proizvodDTO);
		} catch (Exception e) {
			return new ResponseEntity<String>("Loš zahtev, nešto nije u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteProizvod(@PathVariable Integer id){
		Proizvod proizvod = proizvodService.findOne(id);
		if(proizvod != null) {
			proizvodService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
