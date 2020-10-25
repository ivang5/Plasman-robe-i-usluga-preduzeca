package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;

import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;

public class StavkaCenovnikaDTO implements Serializable{
	
	private Integer id;
	private ProizvodDTO proizvod;
	private Double cena;
	
	public StavkaCenovnikaDTO(ProizvodDTO proizvod, Double cena) {
		super();
		this.proizvod = proizvod;
		this.cena = cena;
	}
	
	public StavkaCenovnikaDTO(){}
	
	public StavkaCenovnikaDTO(StavkaCenovnika st){
		this.id = st.getId();
		this.cena = st.getCena();
		this.proizvod = new ProizvodDTO(st.getProizvod());
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

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}
	
	

}
