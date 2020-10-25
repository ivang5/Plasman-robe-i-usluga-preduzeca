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

import com.example.poslovnaInformatikaFTN.dto.VrstaPartneraDTO;

@Entity                 
@Table(name="poslovni_partneri")
public class PoslovniPartner {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_poslovnog_partnera", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="pib", unique=true, nullable=false)
	private int pib;

	@Column(name="naziv", unique=false, nullable=false)
	private String naziv;
	
	@Column(name="vrsta_partnera", unique=false, nullable=false)
	private VrstaPartneraDTO vrstaPartnera;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name = "poslovni_partner_id")
	private Set<RacunUBanci>  racuniUBanci = new HashSet<RacunUBanci>();
	
	@Column(name="adresa", unique=false, nullable=false)
	private String adresa;
	
	@Column(name="telefon", unique=false, nullable=false)
	private String telefon;
	
	@Column(name="email", unique=false, nullable=false)
	private String email;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;

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

	public VrstaPartneraDTO getVrstaPartnera() {
		return vrstaPartnera;
	}

	public void setVrstaPartnera(VrstaPartneraDTO vrstaPartnera) {
		this.vrstaPartnera = vrstaPartnera;
	}

	public Set<RacunUBanci> getRacuniUBanci() {
		return racuniUBanci;
	}

	public void setRacuniUBanci(Set<RacunUBanci> racuniUBanci) {
		this.racuniUBanci = racuniUBanci;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
}
