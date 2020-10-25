package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.Date;

import com.example.poslovnaInformatikaFTN.entity.PDVStopa;

public class PDVStopaDTO implements Serializable{
	
	private Integer id;
	private Double procenat;
	private Date datumPocetkaVazenja;
	private boolean aktivna;
	
	public PDVStopaDTO(Integer id, Double procenat, Date datumPocetkaVazenja, boolean aktivna) {
		super();
		this.id = id;
		this.procenat = procenat;
		this.datumPocetkaVazenja = datumPocetkaVazenja;
		this.aktivna = aktivna;
	}
	
	public PDVStopaDTO() {}
	
	public PDVStopaDTO(PDVStopa pdv) {
		this.id = pdv.getId();
		this.procenat = pdv.getProcenat();
		this.datumPocetkaVazenja = pdv.getDatumPocetkaVazenja();
		this.aktivna = pdv.isAktivna();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getProcenat() {
		return procenat;
	}

	public void setProcenat(Double procenat) {
		this.procenat = procenat;
	}

	public Date getDatumPocetkaVazenja() {
		return datumPocetkaVazenja;
	}

	public void setDatumPocetkaVazenja(Date datumPocetkaVazenja) {
		this.datumPocetkaVazenja = datumPocetkaVazenja;
	}

	public boolean isAktivna() {
		return aktivna;
	}

	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}
	
}
