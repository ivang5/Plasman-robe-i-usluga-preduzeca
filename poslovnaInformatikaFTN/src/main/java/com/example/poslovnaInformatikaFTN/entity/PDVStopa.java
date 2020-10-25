package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity                 
@Table(name="pdv_stope")
public class PDVStopa {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_pdv_stope", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="procenat", unique=false, nullable=false)
	private Double procenat;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datum_pocetka_vazenja", unique=false, nullable=false)
	private Date datumPocetkaVazenja;
	
	@Column(name="aktivna", unique=false, nullable=false)
	private boolean aktivna;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;
	
	@ManyToOne
	@JoinColumn(name="id_pdv_kategorije", referencedColumnName="id_pdv_kategorije", nullable=false)
	private PDVKategorija pdvKategorija;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getProcenat() {
		return procenat;
	}

	public void setProcenat(Double procenat) {
		this.procenat = procenat;
	}

	public Date getDatumPocetkaVazenja() {
		return datumPocetkaVazenja;
	}

	public void setDatumPocetkaVazenja(Date datumPocetkaVazenja) {
		this.datumPocetkaVazenja = datumPocetkaVazenja;
	}

	public boolean isAktivna() {
		return aktivna;
	}

	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}

	public PDVKategorija getPdvKategorija() {
		return pdvKategorija;
	}

	public void setPdvKategorija(PDVKategorija pdvKategorija) {
		this.pdvKategorija = pdvKategorija;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

}
