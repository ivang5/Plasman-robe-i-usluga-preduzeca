package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.Faktura;
import com.example.poslovnaInformatikaFTN.entity.PoslovnaGodina;
import com.example.poslovnaInformatikaFTN.entity.Predracun;

public class PoslovnaGodinaDTO implements Serializable{
	
	private int godina;
	private Double ukupanPorez;
	private boolean zakljucena;
	private List<FakturaDTO> faktureUGodini;
	private List<PredracunDTO> predracuniUGodini;
	
	public PoslovnaGodinaDTO(int godina, Double ukupanPorez, boolean zakljucena, List<FakturaDTO> faktureUGodini,
			List<PredracunDTO> predracuniUGodini) {
		super();
		this.godina = godina;
		this.ukupanPorez = ukupanPorez;
		this.zakljucena = zakljucena;
		this.faktureUGodini = faktureUGodini;
		this.predracuniUGodini = predracuniUGodini;
	}
	
	public PoslovnaGodinaDTO() {}
	
	public PoslovnaGodinaDTO(PoslovnaGodina p) {
		this.godina = p.getGodina();
		this.ukupanPorez = p.getUkupanPorez();
		this.zakljucena = p.isZakljucena();
		List<FakturaDTO> faktureDTO = new ArrayList<FakturaDTO>();
		for(Faktura f : p.getFaktureUGodini()) {
			faktureDTO.add(new FakturaDTO(f));
		}
		this.faktureUGodini = faktureDTO;
		List<PredracunDTO> predracuniDTO = new ArrayList<PredracunDTO>();
		for(Predracun pr : p.getPredracuniUGodini()) {
			predracuniDTO.add(new PredracunDTO(pr));
		}
		this.predracuniUGodini = predracuniDTO;
	}

	public int getGodina() {
		return godina;
	}

	public void setGodina(int godina) {
		this.godina = godina;
	}

	public Double getUkupanPorez() {
		return ukupanPorez;
	}

	public void setUkupanPorez(Double ukupanPorez) {
		this.ukupanPorez = ukupanPorez;
	}

	public boolean isZakljucena() {
		return zakljucena;
	}

	public void setZakljucena(boolean zakljucena) {
		this.zakljucena = zakljucena;
	}

	public List<FakturaDTO> getFaktureUGodini() {
		return faktureUGodini;
	}

	public void setFaktureUGodini(List<FakturaDTO> faktureUGodini) {
		this.faktureUGodini = faktureUGodini;
	}

	public List<PredracunDTO> getPredracuniUGodini() {
		return predracuniUGodini;
	}

	public void setPredracuniUGodini(List<PredracunDTO> predracuniUGodini) {
		this.predracuniUGodini = predracuniUGodini;
	}

}
