package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity                 
@Table(name="predracuni")
public class Predracun {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_predracuna", unique=true, nullable=false)
	private Integer id;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="predracun")
	private Set<StavkaPredracuna> stavkePredracuna = new HashSet<StavkaPredracuna>();
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_kupca", referencedColumnName = "id_poslovnog_partnera", nullable=false)
    private PoslovniPartner kupac;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="rok_isporuke", unique=false, nullable=true)
	private Date rokIsporuke;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="rok_placanja", unique=false, nullable=true)
	private Date rokPlacanja;
	
	@Column(name="broj_racuna", unique=false, nullable=false)
	private String brojRacuna;
	
	@Column(name="iznos", unique=false, nullable=false)
	private Double iznos;
	
	@Column(name="pdv", unique=false, nullable=false)
	private Double pdv;
	
	@Column(name="ukupan_rabat", unique=false, nullable=false)
	private Double ukupanRabat;
	
	@ManyToOne
	@JoinColumn(name="id_zaposlenog", referencedColumnName="id_zaposlenog", nullable=false)
	private Zaposleni izdavalac;
	
	@ManyToOne
	@JoinColumn(name="poslovna_godina", referencedColumnName="godina", nullable=false)
	private PoslovnaGodina godina;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<StavkaPredracuna> getStavkePredracuna() {
		return stavkePredracuna;
	}

	public void setStavkePredracuna(Set<StavkaPredracuna> stavkePredracuna) {
		this.stavkePredracuna = stavkePredracuna;
	}

	public PoslovniPartner getKupac() {
		return kupac;
	}

	public void setKupac(PoslovniPartner kupac) {
		this.kupac = kupac;
	}

	public Date getRokIsporuke() {
		return rokIsporuke;
	}

	public void setRokIsporuke(Date rokIsporuke) {
		this.rokIsporuke = rokIsporuke;
	}

	public Date getRokPlacanja() {
		return rokPlacanja;
	}

	public void setRokPlacanja(Date rokPlacanja) {
		this.rokPlacanja = rokPlacanja;
	}

	public Zaposleni getIzdavalac() {
		return izdavalac;
	}

	public void setIzdavalac(Zaposleni izdavalac) {
		this.izdavalac = izdavalac;
	}

	public PoslovnaGodina getGodina() {
		return godina;
	}

	public void setGodina(PoslovnaGodina godina) {
		this.godina = godina;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public Double getPdv() {
		return pdv;
	}

	public void setPdv(Double pdv) {
		this.pdv = pdv;
	}

	public Double getUkupanRabat() {
		return ukupanRabat;
	}

	public void setUkupanRabat(Double ukupanRabat) {
		this.ukupanRabat = ukupanRabat;
	}
}
