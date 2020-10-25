package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;

import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;

public class RacunUBanciDTO implements Serializable{
	
	private Integer id;
	private String nazivBanke;
	private String brojRacuna;
	private boolean obrisan;
	
	public RacunUBanciDTO() {
		super();
	}
	
	public RacunUBanciDTO(RacunUBanci r) {
		this.id = r.getId();
		this.nazivBanke = r.getNazivBanke();
		this.brojRacuna = r.getBrojRacuna();
		this.obrisan = r.isObrisan();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNazivBanke() {
		return nazivBanke;
	}
	public void setNazivBanke(String nazivBanke) {
		this.nazivBanke = nazivBanke;
	}
	public String getBrojRacuna() {
		return brojRacuna;
	}
	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

}
