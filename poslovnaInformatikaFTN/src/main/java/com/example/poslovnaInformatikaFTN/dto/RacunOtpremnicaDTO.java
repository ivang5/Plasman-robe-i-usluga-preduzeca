package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RacunOtpremnicaDTO implements Serializable{
	
	private Integer id;
	private List<StavkaFaktureDTO> stavkeRacuna;
	private Date datumRacuna;
	private String brojRacunaPlacanja;
	private Double iznos;
	private Double pdv;
	private PoslovniPartnerDTO kupac;
	private String pozivNaBroj;
	private PreduzeceDTO prodavac;
	
	public RacunOtpremnicaDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<StavkaFaktureDTO> getStavkeRacuna() {
		return stavkeRacuna;
	}

	public void setStavkeRacuna(List<StavkaFaktureDTO> stavkeRacuna) {
		this.stavkeRacuna = stavkeRacuna;
	}

	public Date getDatumRacuna() {
		return datumRacuna;
	}

	public void setDatumRacuna(Date datumRacuna) {
		this.datumRacuna = datumRacuna;
	}

	public String getBrojRacunaPlacanja() {
		return brojRacunaPlacanja;
	}

	public void setBrojRacunaPlacanja(String brojRacunaPlacanja) {
		this.brojRacunaPlacanja = brojRacunaPlacanja;
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

	public PoslovniPartnerDTO getKupac() {
		return kupac;
	}

	public void setKupac(PoslovniPartnerDTO kupac) {
		this.kupac = kupac;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public PreduzeceDTO getProdavac() {
		return prodavac;
	}

	public void setProdavac(PreduzeceDTO prodavac) {
		this.prodavac = prodavac;
	}

}
