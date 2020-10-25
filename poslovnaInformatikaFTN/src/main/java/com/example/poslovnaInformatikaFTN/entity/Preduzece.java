package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity                 
@Table(name="preduzece")
public class Preduzece {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_preduzeca", unique=true, nullable=false)
	private Integer id;

	@Column(name="pib", unique=true, nullable=false)
	private int pib;
	
	@Column(name="naziv", unique=true, nullable=false)
	private String naziv;
	
	@Column(name="telefon", unique=false, nullable=true)
	private String telefon;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name = "preduzece_id")
	private Set<RacunUBanci>  racuniUBanci = new HashSet<RacunUBanci>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="preduzece")
	private Set<PoslovnaGodina> poslovneGodine = new HashSet<PoslovnaGodina>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="preduzece")
	private Set<Zaposleni> zaposleni = new HashSet<Zaposleni>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="preduzece")
	private Set<GrupaProizvoda> grupeProizvoda = new HashSet<GrupaProizvoda>();

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

	public Set<RacunUBanci> getRacuniUBanci() {
		return racuniUBanci;
	}

	public void setRacuniUBanci(Set<RacunUBanci> racuniUBanci) {
		this.racuniUBanci = racuniUBanci;
	}

	public Set<PoslovnaGodina> getPoslovneGodine() {
		return poslovneGodine;
	}

	public void setPoslovneGodine(Set<PoslovnaGodina> poslovneGodine) {
		this.poslovneGodine = poslovneGodine;
	}

	public Set<Zaposleni> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(Set<Zaposleni> zaposleni) {
		this.zaposleni = zaposleni;
	}

	public Set<GrupaProizvoda> getGrupeProizvoda() {
		return grupeProizvoda;
	}

	public void setGrupeProizvoda(Set<GrupaProizvoda> grupeProizvoda) {
		this.grupeProizvoda = grupeProizvoda;
	}
	
}
