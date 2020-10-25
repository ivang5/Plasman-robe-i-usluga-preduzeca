package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity                 
@Table(name="pdv_kategorije")
public class PDVKategorija {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_pdv_kategorije", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="naziv", unique=true, nullable=false)
	private String naziv;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="pdvKategorija")
	private Set<GrupaProizvoda> grupeProizvoda = new HashSet<GrupaProizvoda>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="pdvKategorija")
	private Set<PDVStopa> stopePDV = new HashSet<PDVStopa>();

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

	public Set<GrupaProizvoda> getGrupeProizvoda() {
		return grupeProizvoda;
	}

	public void setGrupeProizvoda(Set<GrupaProizvoda> grupeProizvoda) {
		this.grupeProizvoda = grupeProizvoda;
	}

	public Set<PDVStopa> getStopePDV() {
		return stopePDV;
	}

	public void setStopePDV(Set<PDVStopa> stopePDV) {
		this.stopePDV = stopePDV;
	}

}
