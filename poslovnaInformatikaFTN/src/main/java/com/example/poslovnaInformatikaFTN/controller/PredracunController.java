package com.example.poslovnaInformatikaFTN.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.FakturaDTO;
import com.example.poslovnaInformatikaFTN.dto.PredracunDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunOtpremnicaDTO;
import com.example.poslovnaInformatikaFTN.dto.StatusFaktureDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaFaktureDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaPredracunaDTO;
import com.example.poslovnaInformatikaFTN.entity.Faktura;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.Predracun;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.FakturaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PoslovnaGodinaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PoslovniPartnerServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PredracunServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ProizvodServiceInterface;
import com.example.poslovnaInformatikaFTN.service.StavkaFaktureServiceInterface;
import com.example.poslovnaInformatikaFTN.service.StavkaPredracunaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ZaposleniServiceInterface;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@RestController
@RequestMapping(value="api/predracuni")
public class PredracunController {
	
	@Autowired
	private PredracunServiceInterface predracunService;
	
	@Autowired
	private ZaposleniServiceInterface zaposleniService;
	
	@Autowired
	private FakturaServiceInterface fakturaService;
	
	@Autowired
	private PoslovniPartnerServiceInterface poslovniPartnerService;
	
	@Autowired
	private PoslovnaGodinaServiceInterface poslovnaGodinaService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<PredracunDTO>getPredracunById(@PathVariable("id") int id){
		Predracun predracun = predracunService.findOne(id);
		
		if(predracun == null){
			return new ResponseEntity<PredracunDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PredracunDTO>(new PredracunDTO(predracun), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PredracunDTO>>getAllPredracuni(){
		return new ResponseEntity<List<PredracunDTO>>(predracunService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createPredracun(@RequestBody PredracunDTO predracunDTO){
		if(predracunDTO.getIdIzdavaoca() == null || predracunDTO.getIdIzdavaoca() < 0 || zaposleniService.findOne(predracunDTO.getIdIzdavaoca()) == null)
			return new ResponseEntity<String>("Izdavalac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getStavkePredracuna() == null || predracunDTO.getStavkePredracuna().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke predracuna!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getRokIsporuke() == null || predracunDTO.getRokIsporuke().before(new Date()))
			return new ResponseEntity<String>("Rok isporuke mora biti u buducnosti!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getRokPlacanja() == null || predracunDTO.getRokPlacanja().before(new Date()))
			return new ResponseEntity<String>("Rok placanja mora biti u buducnosti!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getIznos() == null || predracunDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getPdv() == null || predracunDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getUkupanRabat().isNaN())
			return new ResponseEntity<String>("Pogresan unos rabata!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getKupac() == null || predracunDTO.getKupac().getId() == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(poslovnaGodinaService.findOne(predracunDTO.getPoslovnaGodina()) == null)
			return new ResponseEntity<String>("Nisi poslao godinu", HttpStatus.BAD_REQUEST);
		if(poslovniPartnerService.findOne(predracunDTO.getKupac().getId()) == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);

		try {
			predracunService.save(predracunDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Stavke fakture nisu u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping(value="/generateFaktura")
	public ResponseEntity<?> generateFaktura(@RequestBody PredracunDTO predracunDTO, @RequestHeader("poziv-na-broj") String pozivNaBroj){
		if(predracunDTO.getIdIzdavaoca() == null || predracunDTO.getIdIzdavaoca() < 0 || zaposleniService.findOne(predracunDTO.getIdIzdavaoca()) == null)
			return new ResponseEntity<String>("Izdavalac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getStavkePredracuna() == null || predracunDTO.getStavkePredracuna().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke predracuna!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getRokIsporuke() == null || predracunDTO.getRokIsporuke().before(new Date()))
			return new ResponseEntity<String>("Rok isporuke mora biti u buducnosti!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getRokPlacanja() == null || predracunDTO.getRokPlacanja().before(new Date()))
			return new ResponseEntity<String>("Rok placanja mora biti u buducnosti!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getIznos() == null || predracunDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getPdv() == null || predracunDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getUkupanRabat().isNaN())
			return new ResponseEntity<String>("Pogresan unos rabata!", HttpStatus.BAD_REQUEST);
		if(predracunDTO.getKupac() == null || predracunDTO.getKupac().getId() == null || poslovniPartnerService.findOne(predracunDTO.getKupac().getId()) == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		Faktura faktura = null;
		
		try {
			faktura = fakturaService.generateFaktura(predracunDTO, pozivNaBroj);
		}catch (Exception e) {
			return new ResponseEntity<String>("Los zahtev, nesto nije u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<FakturaDTO>(new FakturaDTO(faktura),HttpStatus.CREATED);
	}

}
