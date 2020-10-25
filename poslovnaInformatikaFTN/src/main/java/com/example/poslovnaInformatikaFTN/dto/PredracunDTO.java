package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.Predracun;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;

public class PredracunDTO implements Serializable{
	
	private Integer id;
	private List<StavkaPredracunaDTO> stavkePredracuna;
	private PoslovniPartnerDTO kupac;
	private String brojRacuna;
	private Double iznos;
	private Double pdv;
	private Double ukupanRabat;
	private Date rokIsporuke;
	private Date rokPlacanja;
	private Integer idIzdavaoca;
	private Integer poslovnaGodina;

	public PredracunDTO(Predracun p) {
		this.id = p.getId();
		this.idIzdavaoca = p.getIzdavalac().getId();
    	List<StavkaPredracunaDTO> stavkePredracunaDTO = new ArrayList<>();
    	for (StavkaPredracuna itStavkaPredracuna : p.getStavkePredracuna()) {
    		stavkePredracunaDTO.add(new StavkaPredracunaDTO(itStavkaPredracuna));
		}
		this.stavkePredracuna = stavkePredracunaDTO;
		this.rokIsporuke = p.getRokIsporuke();
		this.rokPlacanja = p.getRokPlacanja();
		this.brojRacuna = p.getBrojRacuna();
		this.iznos = p.getIznos();
		this.pdv = p.getPdv();
		this.ukupanRabat = p.getUkupanRabat();
		this.kupac = new PoslovniPartnerDTO(p.getKupac());
		this.poslovnaGodina = p.getGodina().getGodina();
	}

	public PredracunDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<StavkaPredracunaDTO> getStavkePredracuna() {
		return stavkePredracuna;
	}

	public void setStavkePredracuna(List<StavkaPredracunaDTO> stavkePredracuna) {
		this.stavkePredracuna = stavkePredracuna;
	}

	public PoslovniPartnerDTO getKupac() {
		return kupac;
	}

	public void setKupac(PoslovniPartnerDTO kupac) {
		this.kupac = kupac;
	}

	public Date getRokIsporuke() {
		return rokIsporuke;
	}

	public void setRokIsporuke(Date rokIsporuke) {
		this.rokIsporuke = rokIsporuke;
	}

	public Date getRokPlacanja() {
		return rokPlacanja;
	}

	public void setRokPlacanja(Date rokPlacanja) {
		this.rokPlacanja = rokPlacanja;
	}

	public Integer getIdIzdavaoca() {
		return idIzdavaoca;
	}

	public void setIdIzdavaoca(Integer idIzdavaoca) {
		this.idIzdavaoca = idIzdavaoca;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public Double getPdv() {
		return pdv;
	}

	public void setPdv(Double pdv) {
		this.pdv = pdv;
	}

	public Double getUkupanRabat() {
		return ukupanRabat;
	}

	public void setUkupanRabat(Double ukupanRabat) {
		this.ukupanRabat = ukupanRabat;
	}

	public Integer getPoslovnaGodina() {
		return poslovnaGodina;
	}

	public void setPoslovnaGodina(Integer poslovnaGodina) {
		this.poslovnaGodina = poslovnaGodina;
	}

}
