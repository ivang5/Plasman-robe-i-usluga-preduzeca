package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Preduzece;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;

public class PreduzeceDTO implements Serializable{
	
	private Integer id;
	private int pib;
	private String naziv;
	private String telefon;
	private List<RacunUBanciDTO> racuniUBanci;
	private List<ZaposleniDTO> zaposleni;
	
	public PreduzeceDTO(Integer id, int pib, String naziv, String telefon, List<RacunUBanciDTO> racuniUBanci, List<ZaposleniDTO> zaposleni) {
		super();
		this.id = id;
		this.pib = pib;
		this.naziv = naziv;
		this.telefon = telefon;
		this.racuniUBanci = racuniUBanci;
		this.zaposleni = zaposleni;
	}
	
	public PreduzeceDTO(Preduzece p) {
		this.id = p.getId();
		this.pib = p.getPib();
		this.naziv = p.getNaziv();
		this.telefon = p.getTelefon();
    	List<RacunUBanciDTO> racuniUBanciDTO = new ArrayList<>();
    	for (RacunUBanci itRacunUBanci : p.getRacuniUBanci()) {
    		if(!itRacunUBanci.isObrisan())
    			racuniUBanciDTO.add(new RacunUBanciDTO(itRacunUBanci));
		}
		this.racuniUBanci = racuniUBanciDTO;
    	List<ZaposleniDTO> zaposleniDTO = new ArrayList<>();
    	for (Zaposleni itZaposleni : p.getZaposleni()) {
    		if(!itZaposleni.isObrisan())
    			zaposleniDTO.add(new ZaposleniDTO(itZaposleni));
		}
    	this.zaposleni = zaposleniDTO;
	}

	public PreduzeceDTO() {
		super();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPib() {
		return pib;
	}

	public void setPib(int pib) {
		this.pib = pib;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public List<RacunUBanciDTO> getRacuniUBanci() {
		return racuniUBanci;
	}

	public void setRacuniUBanci(List<RacunUBanciDTO> racuniUBanci) {
		this.racuniUBanci = racuniUBanci;
	}

	public List<ZaposleniDTO> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(List<ZaposleniDTO> zaposleni) {
		this.zaposleni = zaposleni;
	}

}
