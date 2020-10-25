package com.example.poslovnaInformatikaFTN.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.poslovnaInformatikaFTN.dto.FakturaDTO;
import com.example.poslovnaInformatikaFTN.dto.PredracunDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunOtpremnicaDTO;
import com.example.poslovnaInformatikaFTN.entity.Faktura;

public interface FakturaServiceInterface {
	
	public Faktura findOne(Integer id);
	
	public List<FakturaDTO> findAll();
	
	public List<FakturaDTO> getAllFakturaInDateRange(Date dateStart, Date dateEnd);
	
	public void save(FakturaDTO fakturaDTO);
	
	public void update(FakturaDTO fakturaDTO);
	
	public void remove(Integer id);
	
	public RacunOtpremnicaDTO generateRacunOtpremnica(@RequestBody FakturaDTO fakturaDTO);

	public byte[] generateRacunPdf(RacunOtpremnicaDTO racunOtpremnicaDTO);
	
	public Faktura generateFaktura(PredracunDTO predracunDTO, String pozivNaBroj);
}
