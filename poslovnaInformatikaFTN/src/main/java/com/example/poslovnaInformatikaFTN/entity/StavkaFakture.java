package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity                 
@Table(name="stavkeFakture")
public class StavkaFakture {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_stavke_fakture", unique=true, nullable=false)
	private Integer id;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proizvoda", referencedColumnName = "id_proizvoda", nullable=false)
    private Proizvod proizvod;
    
	@Column(name="kolicina", unique=false, nullable=false)
	private Double kolicina;
    
	@Column(name="cena", unique=false, nullable=false)
	private Double cena;
	
	@Column(name="pdv", unique=false, nullable=false)
	private Double pdv;
	
	@Column(name="rabat", unique=false, nullable=true)
	private Double rabat;
	
	@Column(name="iznos", unique=false, nullable=false)
	private Double iznos;
	
	@ManyToOne
	@JoinColumn(name="id_fakture", referencedColumnName="id_fakture", nullable=false)
	private Faktura faktura;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proizvod getProizvod() {
		return proizvod;
	}

	public void setProizvod(Proizvod proizvod) {
		this.proizvod = proizvod;
	}

	public Double getKolicina() {
		return kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Double getPdv() {
		return pdv;
	}

	public void setPdv(Double pdv) {
		this.pdv = pdv;
	}

	public Double getRabat() {
		return rabat;
	}

	public void setRabat(Double rabat) {
		this.rabat = rabat;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public Faktura getFaktura() {
		return faktura;
	}

	public void setFaktura(Faktura faktura) {
		this.faktura = faktura;
	}
	
}
