package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;

import com.example.poslovnaInformatikaFTN.entity.Proizvod;

public class ProizvodDTO implements Serializable{

	private Integer id;
	private String naziv;
	private String jedinicaMere;
	private String opis;
	private TipProizvodaDTO tipProizvoda;
	private Integer idGrupe;
	
	public ProizvodDTO(Integer id, String naziv, String jedinicaMere, String opis, TipProizvodaDTO tipProizvoda,
			Integer idGrupe) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jedinicaMere = jedinicaMere;
		this.opis = opis;
		this.tipProizvoda = tipProizvoda;
		this.idGrupe = idGrupe;
	}

	public ProizvodDTO() {}
	
	public ProizvodDTO(Proizvod p) {
		this.id = p.getId();
		this.naziv = p.getNaziv();
		this.jedinicaMere = p.getJedinicaMere();
		this.opis = p.getOpis();
		this.tipProizvoda = TipProizvodaDTO.valueOf(p.getTipProizvoda().toString());
		this.idGrupe = p.getGrupa().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public TipProizvodaDTO getTipProizvoda() {
		return tipProizvoda;
	}

	public void setTipProizvoda(TipProizvodaDTO tipProizvoda) {
		this.tipProizvoda = tipProizvoda;
	}

	public Integer getIdGrupe() {
		return idGrupe;
	}

	public void setIdGrupe(Integer idGrupe) {
		this.idGrupe = idGrupe;
	}
	
}
