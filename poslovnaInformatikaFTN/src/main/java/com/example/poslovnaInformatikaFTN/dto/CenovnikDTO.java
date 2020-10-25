package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.Cenovnik;
import com.example.poslovnaInformatikaFTN.entity.StavkaCenovnika;

public class CenovnikDTO implements Serializable{

	private Integer id;
	private List<StavkaCenovnikaDTO> stavkeCenovnika;
	private Date datumIzdavanja;
	
	public CenovnikDTO() {
		
	}
	
	public CenovnikDTO(Cenovnik c) {
		this.id = c.getId();
		this.datumIzdavanja = c.getDatumIzdavanja();
    	List<StavkaCenovnikaDTO> stavkeCenovnikaDTOs = new ArrayList<>();
    	for (StavkaCenovnika itStavkaCenovnika : c.getStavkeCenovnika()) {
    		stavkeCenovnikaDTOs.add(new StavkaCenovnikaDTO(itStavkaCenovnika));
		}
    	this.stavkeCenovnika = stavkeCenovnikaDTOs;
	}
	
	public CenovnikDTO(List<StavkaCenovnikaDTO> stavkeCenovnika, Date datumIzdavanja) {
		super();
		this.stavkeCenovnika = stavkeCenovnika;
		this.datumIzdavanja = datumIzdavanja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<StavkaCenovnikaDTO> getStavkeCenovnika() {
		return stavkeCenovnika;
	}

	public void setStavkeCenovnika(List<StavkaCenovnikaDTO> stavkeCenovnika) {
		this.stavkeCenovnika = stavkeCenovnika;
	}

	public Date getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}
	
	
	
	
}
