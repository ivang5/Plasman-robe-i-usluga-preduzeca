package com.example.poslovnaInformatikaFTN.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.MediaSize.Other;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.poslovnaInformatikaFTN.dto.FakturaDTO;
import com.example.poslovnaInformatikaFTN.dto.PreduzeceDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunOtpremnicaDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaFaktureDTO;
import com.example.poslovnaInformatikaFTN.entity.Faktura;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.service.FakturaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PoslovnaGodinaServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PoslovniPartnerServiceInterface;
import com.example.poslovnaInformatikaFTN.service.PreduzeceServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ProizvodServiceInterface;
import com.example.poslovnaInformatikaFTN.service.StavkaFaktureServiceInterface;
import com.example.poslovnaInformatikaFTN.service.ZaposleniServiceInterface;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@RestController
@RequestMapping(value="api/fakture")
public class FakturaController {
	
	@Autowired
	private FakturaServiceInterface fakturaService;
	
	@Autowired
	private ZaposleniServiceInterface zaposleniService;
	
	@Autowired
	private PoslovniPartnerServiceInterface poslovniPartnerService;
	
	@Autowired
	private PoslovnaGodinaServiceInterface poslovnaGodinaService;
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<FakturaDTO>getFakturaById(@PathVariable("id") int id){
		Faktura faktura = fakturaService.findOne(id);
		
		if(faktura == null){
			return new ResponseEntity<FakturaDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FakturaDTO>(new FakturaDTO(faktura), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<FakturaDTO>>getAllFaktura(){
		return new ResponseEntity<List<FakturaDTO>>(fakturaService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/inRange")
	public ResponseEntity<?>getAllFakturaInDateRange(@RequestHeader("date-start") String dateStart, @RequestHeader("date-end") String dateEnd){ 
		Date startDate = null;
		Date endDate = null;
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			startDate = formatter.parse(dateStart);
			endDate = formatter.parse(dateEnd);
		}catch (Exception e) {
			return new ResponseEntity<String>("Format datuma nije dobar", HttpStatus.BAD_REQUEST);
		}
		List<FakturaDTO> faktureDTO = fakturaService.getAllFakturaInDateRange(startDate, endDate);
		return new ResponseEntity<List<FakturaDTO>>(faktureDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createFaktura(@RequestBody FakturaDTO fakturaDTO){
		if(fakturaDTO.getIdIdavaoca() == null || fakturaDTO.getIdIdavaoca() < 0 || zaposleniService.findOne(fakturaDTO.getIdIdavaoca()) == null)
			return new ResponseEntity<String>("Izdavalac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStavkeFakture() == null || fakturaDTO.getStavkeFakture().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumFakture() == null || fakturaDTO.getDatumFakture().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumValute() == null || fakturaDTO.getDatumValute().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum valute!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getIznos() == null || fakturaDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPdv() == null || fakturaDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getUkupanRabat().isNaN())
			return new ResponseEntity<String>("Pogresan unos rabata!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStatus() == null)
			return new ResponseEntity<String>("Nisi mi dao status fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getKupac() == null || fakturaDTO.getKupac().getId() == null || poslovniPartnerService.findOne(fakturaDTO.getKupac().getId()) == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPozivNaBroj().isEmpty())
			return new ResponseEntity<String>("Poziv na broj nisi uneo", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPoslovnaGodina() == null || poslovnaGodinaService.findOne(fakturaDTO.getPoslovnaGodina()) == null)
			return new ResponseEntity<String>("Poslovnu godinu nisi uneo", HttpStatus.BAD_REQUEST);
		
		try {
			fakturaService.save(fakturaDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Los zahtev, nesto nije u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<?> updateFaktura(@RequestBody FakturaDTO fakturaDTO){
		if(fakturaDTO.getId() == null)
			return new ResponseEntity<String>("A gde ti je id!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getIdIdavaoca() == null || fakturaDTO.getIdIdavaoca() < 0 || zaposleniService.findOne(fakturaDTO.getIdIdavaoca()) == null)
			return new ResponseEntity<String>("Izdavalac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStavkeFakture() == null || fakturaDTO.getStavkeFakture().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumFakture() == null || fakturaDTO.getDatumFakture().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumValute() == null || fakturaDTO.getDatumValute().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum valute!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getIznos() == null || fakturaDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPdv() == null || fakturaDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getUkupanRabat().isNaN())
			return new ResponseEntity<String>("Pogresan unos rabata!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStatus() == null)
			return new ResponseEntity<String>("Nisi mi dao status fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getKupac() == null || fakturaDTO.getKupac().getId() == null || poslovniPartnerService.findOne(fakturaDTO.getKupac().getId()) == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPozivNaBroj().isEmpty())
			return new ResponseEntity<String>("Poziv na broj nisi uneo", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPoslovnaGodina() == null || poslovnaGodinaService.findOne(fakturaDTO.getPoslovnaGodina()) == null)
			return new ResponseEntity<String>("Poslovnu godinu nisi uneo", HttpStatus.BAD_REQUEST);
		
		try {
			fakturaService.update(fakturaDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>("Los zahtev, nesto nije u redu", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping(value="/generateRacunOtpremnica")
	public ResponseEntity<?> generateRacunOtpremnica(@RequestBody FakturaDTO fakturaDTO){
		if(fakturaDTO.getIdIdavaoca() == null || fakturaDTO.getIdIdavaoca() < 0)
			return new ResponseEntity<String>("Izdavalac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStavkeFakture() == null || fakturaDTO.getStavkeFakture().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumFakture() == null || fakturaDTO.getDatumFakture().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getDatumValute() == null || fakturaDTO.getDatumValute().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum valute!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getBrojRacuna().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getIznos() == null || fakturaDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPdv() == null || fakturaDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getUkupanRabat().isNaN())
			return new ResponseEntity<String>("Pogresan unos rabata!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getStatus() == null)
			return new ResponseEntity<String>("Nisi mi dao status fakture!", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getKupac() == null || fakturaDTO.getKupac().getId() == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(fakturaDTO.getPozivNaBroj().isEmpty())
			return new ResponseEntity<String>("Poziv na broj nisi uneo", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<RacunOtpremnicaDTO>(fakturaService.generateRacunOtpremnica(fakturaDTO), HttpStatus.CREATED);
	}
	
	@PostMapping(value="/downloadRacun")
	public ResponseEntity<?> downloadRacun(@RequestBody RacunOtpremnicaDTO racunOtpremnicaDTO){
		if(racunOtpremnicaDTO.getStavkeRacuna() == null || racunOtpremnicaDTO.getStavkeRacuna().isEmpty())
			return new ResponseEntity<String>("Moras imati stavke racuna!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getDatumRacuna() == null || racunOtpremnicaDTO.getDatumRacuna().after(new Date()))
			return new ResponseEntity<String>("Nisi dobro uneo datum racuna!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getBrojRacunaPlacanja().isEmpty())
			return new ResponseEntity<String>("A gde ti je broj racuna!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getIznos() == null || racunOtpremnicaDTO.getIznos().isNaN())
			return new ResponseEntity<String>("Pogresan unos iznosa!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getPdv() == null || racunOtpremnicaDTO.getPdv().isNaN())
			return new ResponseEntity<String>("Pogresan unos pdv-a!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getProdavac() == null)
			return new ResponseEntity<String>("Nije unet prodavac!", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getKupac() == null || racunOtpremnicaDTO.getKupac().getId() == null)
			return new ResponseEntity<String>("Kupac nepoznat sistemu", HttpStatus.BAD_REQUEST);
		if(racunOtpremnicaDTO.getPozivNaBroj().isEmpty())
			return new ResponseEntity<String>("Poziv na broj nisi uneo", HttpStatus.BAD_REQUEST);
		
	      String filename = "racun-otpremnica" + racunOtpremnicaDTO.getId() + ".pdf";

	      HttpHeaders headers = new HttpHeaders();
	      headers.add("filename", filename);
	      byte[] bFile = fakturaService.generateRacunPdf(racunOtpremnicaDTO);
	      
	      return ResponseEntity.ok().headers(headers).body(bFile);
		}
	


}
