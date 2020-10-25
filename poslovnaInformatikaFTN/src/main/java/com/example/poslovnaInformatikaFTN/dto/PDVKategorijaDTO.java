package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.PDVKategorija;
import com.example.poslovnaInformatikaFTN.entity.PDVStopa;

public class PDVKategorijaDTO implements Serializable{

	private Integer id;
	private String naziv;
	private List<GrupaProizvodaDTO> grupeProizvoda;
	private List<PDVStopaDTO> stopePDV;
	
	public PDVKategorijaDTO(Integer id, String naziv, List<GrupaProizvodaDTO> grupeProizvoda,
			List<PDVStopaDTO> stopePDV) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.grupeProizvoda = grupeProizvoda;
		this.stopePDV = stopePDV;
	}
	
	public PDVKategorijaDTO() {}
	
	public PDVKategorijaDTO(PDVKategorija p) {
		this.id = p.getId();
		this.naziv = p.getNaziv();
    	List<PDVStopaDTO> pdvStopeDTO = new ArrayList<>();
    	for (PDVStopa itStopa : p.getStopePDV()) {
    		pdvStopeDTO.add(new PDVStopaDTO(itStopa));
		}
		this.stopePDV = pdvStopeDTO;
    	List<GrupaProizvodaDTO> grupeProizvodaDTO = new ArrayList<>();
    	for (GrupaProizvoda itGrupa : p.getGrupeProizvoda()) {
    		grupeProizvodaDTO.add(new GrupaProizvodaDTO(itGrupa));
		}
		this.grupeProizvoda = grupeProizvodaDTO;
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

	public List<GrupaProizvodaDTO> getGrupeProizvoda() {
		return grupeProizvoda;
	}

	public void setGrupeProizvoda(List<GrupaProizvodaDTO> grupeProizvoda) {
		this.grupeProizvoda = grupeProizvoda;
	}

	public List<PDVStopaDTO> getStopePDV() {
		return stopePDV;
	}

	public void setStopePDV(List<PDVStopaDTO> stopePDV) {
		this.stopePDV = stopePDV;
	}
	
	
}
