package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity                 
@Table(name="racuni_u_banci")
public class RacunUBanci {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_racuna", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="naziv_banke", unique=false, nullable=false)
	private String nazivBanke;
	
	@Column(name="broj_racuna", unique=true, nullable=false)
	private String brojRacuna;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNazivBanke() {
		return nazivBanke;
	}

	public void setNazivBanke(String nazivBanke) {
		this.nazivBanke = nazivBanke;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
}
