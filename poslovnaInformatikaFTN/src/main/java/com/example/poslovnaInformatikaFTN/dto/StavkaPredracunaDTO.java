package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;

import com.example.poslovnaInformatikaFTN.entity.StavkaPredracuna;

public class StavkaPredracunaDTO implements Serializable{
	
	private Integer id;
	private ProizvodDTO proizvod;
	private Double kolicina;
	private Double cena;
	private Double pdv;
	private Double rabat;
	private Double iznos;
	
	public StavkaPredracunaDTO(Integer id, ProizvodDTO proizvod, Double kolicina, Double cena, Double pdv, Double rabat,
			Double iznos) {
		super();
		this.id = id;
		this.proizvod = proizvod;
		this.kolicina = kolicina;
		this.cena = cena;
		this.pdv = pdv;
		this.rabat = rabat;
		this.iznos = iznos;
	}

	public StavkaPredracunaDTO() {}
	
	public StavkaPredracunaDTO(StavkaPredracuna st) {
		this.id = st.getId();
		this.proizvod = new ProizvodDTO(st.getProizvod());
		this.kolicina = st.getKolicina();
		this.cena = st.getCena();
		this.pdv = st.getPdv();
		this.rabat = st.getRabat();
		this.iznos = st.getIznos();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProizvodDTO getProizvod() {
		return proizvod;
	}

	public void setProizvod(ProizvodDTO proizvod) {
		this.proizvod = proizvod;
	}

	public Double getKolicina() {
		return kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Double getPdv() {
		return pdv;
	}

	public void setPdv(Double pdv) {
		this.pdv = pdv;
	}

	public Double getRabat() {
		return rabat;
	}

	public void setRabat(Double rabat) {
		this.rabat = rabat;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}
	
}
