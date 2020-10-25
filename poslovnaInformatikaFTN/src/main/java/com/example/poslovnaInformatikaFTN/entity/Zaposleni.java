package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.poslovnaInformatikaFTN.dto.TipProizvodaDTO;
import com.example.poslovnaInformatikaFTN.dto.UlogaZaposlenogDTO;

@Entity                 
@Table(name="zaposleni")
public class Zaposleni implements Serializable{

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_zaposlenog", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="ime", unique=false, nullable=false)
	private String ime;
	
	@Column(name="prezime", unique=false, nullable=false)
	private String prezime;
	
	@Column(name="adresa", unique=false, nullable=true)
	private String adresa;
	
	@Column(name="broj_telefona", unique=false, nullable=true)
	private String brojTelefona;
	
	@Column(name="uloga", unique=false, nullable=false)
	private UlogaZaposlenogDTO uloga;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="izdavalac")
	private Set<Faktura> faktureZaposlenog = new HashSet<Faktura>();
	
	@ManyToOne
	@JoinColumn(name="id_preduzeca", referencedColumnName="id_preduzeca", nullable=false)
	private Preduzece preduzece;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public UlogaZaposlenogDTO getUloga() {
		return uloga;
	}

	public void setUloga(UlogaZaposlenogDTO uloga) {
		this.uloga = uloga;
	}

	public Set<Faktura> getFaktureZaposlenog() {
		return faktureZaposlenog;
	}

	public void setFaktureZaposlenog(Set<Faktura> faktureZaposlenog) {
		this.faktureZaposlenog = faktureZaposlenog;
	}

	public Preduzece getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
}
