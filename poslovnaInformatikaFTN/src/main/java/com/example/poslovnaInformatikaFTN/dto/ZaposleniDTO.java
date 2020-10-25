package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.Zaposleni;

public class ZaposleniDTO implements Serializable{
	
	private Integer id;
	private String ime;
	private String prezime;
	private String adresa;
	private String brojTelefona;
	private UlogaZaposlenogDTO uloga;
	
	public ZaposleniDTO(String ime, String prezime, String adresa, String brojTelefona, UlogaZaposlenogDTO uloga) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.uloga = uloga;
	}
	
	public ZaposleniDTO() {}
	
	public ZaposleniDTO(Zaposleni z) {
		this.id = z.getId();
		this.ime = z.getIme();
		this.prezime = z.getPrezime();
		this.adresa = z.getAdresa();
		this.brojTelefona = z.getBrojTelefona();
		this.uloga = UlogaZaposlenogDTO.valueOf(z.getUloga().toString());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public UlogaZaposlenogDTO getUloga() {
		return uloga;
	}

	public void setUloga(UlogaZaposlenogDTO uloga) {
		this.uloga = uloga;
	}

}
