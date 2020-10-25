package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.GrupaProizvoda;
import com.example.poslovnaInformatikaFTN.entity.Proizvod;

public class GrupaProizvodaDTO implements Serializable{
	
	private Integer id;
	private String naziv;
	private Integer idPdvKategorije;
	private List<ProizvodDTO> proizvodi;

	public GrupaProizvodaDTO(Integer id, String naziv, Integer idPdvKategorije, List<ProizvodDTO> proizvodi) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.idPdvKategorije = idPdvKategorije;
		this.proizvodi = proizvodi;
	}

	public GrupaProizvodaDTO() {}
	
	public GrupaProizvodaDTO(GrupaProizvoda gr) {
		this.id = gr.getId();
		this.naziv = gr.getNaziv();
		this.idPdvKategorije = gr.getPdvKategorija().getId();
    	List<ProizvodDTO> proizvodiDTO = new ArrayList<>();
    	for (Proizvod itProizvod : gr.getProizvodi()) {
    		proizvodiDTO.add(new ProizvodDTO(itProizvod));
		}
		this.proizvodi = proizvodiDTO;
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

	public List<ProizvodDTO> getProizvodi() {
		return proizvodi;
	}

	public void setProizvodi(List<ProizvodDTO> proizvodi) {
		this.proizvodi = proizvodi;
	}

	public Integer getIdPdvKategorije() {
		return idPdvKategorije;
	}

	public void setIdPdvKategorije(Integer idPdvKategorije) {
		this.idPdvKategorije = idPdvKategorije;
	}
	
}
