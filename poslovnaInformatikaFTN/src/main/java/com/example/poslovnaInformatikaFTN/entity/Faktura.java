package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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

import com.example.poslovnaInformatikaFTN.dto.StatusFaktureDTO;

@Entity                 
@Table(name="fakture")
public class Faktura implements Serializable{
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_fakture", unique=true, nullable=false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_zaposlenog", referencedColumnName="id_zaposlenog", nullable=false)
	private Zaposleni izdavalac;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="faktura")
	private Set<StavkaFakture> stavkeFakture = new HashSet<StavkaFakture>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datum_fakture", unique=false, nullable=false)
	private Date datumFakture;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datum_valute", unique=false, nullable=false)
	private Date datumValute;
	
	@Column(name="broj_racuna", unique=false, nullable=false)
	private String brojRacuna;
	
	@Column(name="iznos", unique=false, nullable=false)
	private Double iznos;
	
	@Column(name="pdv", unique=false, nullable=false)
	private Double pdv;
	
	@Column(name="ukupan_rabat", unique=false, nullable=false)
	private Double ukupanRabat;
	
	@Column(name="status", unique=false, nullable=false)
	private StatusFaktureDTO status;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_poslovnog_partnera", referencedColumnName = "id_poslovnog_partnera", nullable=false)
    private PoslovniPartner kupac;
    
	@Column(name="poziv_na_broj", unique=false, nullable=true)
	private String pozivNaBroj;
	
	@ManyToOne
	@JoinColumn(name="id_poslovne_godine", referencedColumnName="godina", nullable=false)
	private PoslovnaGodina godina;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Zaposleni getIzdavalac() {
		return izdavalac;
	}

	public void setIzdavalac(Zaposleni izdavalac) {
		this.izdavalac = izdavalac;
	}

	public Set<StavkaFakture> getStavkeFakture() {
		return stavkeFakture;
	}

	public void setStavkeFakture(Set<StavkaFakture> stavkeFakture) {
		this.stavkeFakture = stavkeFakture;
	}

	public Date getDatumFakture() {
		return datumFakture;
	}

	public void setDatumFakture(Date datumFakture) {
		this.datumFakture = datumFakture;
	}

	public Date getDatumValute() {
		return datumValute;
	}

	public void setDatumValute(Date datumValute) {
		this.datumValute = datumValute;
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

	public StatusFaktureDTO getStatus() {
		return status;
	}

	public void setStatus(StatusFaktureDTO status) {
		this.status = status;
	}

	public PoslovniPartner getKupac() {
		return kupac;
	}

	public void setKupac(PoslovniPartner kupac) {
		this.kupac = kupac;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public PoslovnaGodina getGodina() {
		return godina;
	}

	public void setGodina(PoslovnaGodina godina) {
		this.godina = godina;
	}

}
