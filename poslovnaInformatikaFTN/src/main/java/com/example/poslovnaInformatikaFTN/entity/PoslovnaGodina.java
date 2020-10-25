package com.example.poslovnaInformatikaFTN.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity                 
@Table(name="poslovne_godine")
public class PoslovnaGodina{
	
	@Id
	@Column(name="godina", unique=true, nullable=false)
	private int godina;
	
	@Column(name="ukupan_porez", unique=false, nullable=true)
	private Double ukupanPorez;
	
	@Column(name="zakljucena", unique=false, nullable=false)
	private boolean zakljucena;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="godina")
	private Set<Faktura> faktureUGodini = new HashSet<Faktura>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="godina")
	private Set<Predracun> predracuniUGodini = new HashSet<Predracun>();
	
	@ManyToOne
	@JoinColumn(name="id_preduzeca", referencedColumnName="id_preduzeca", nullable=false)
	private Preduzece preduzece;
	
	public PoslovnaGodina() {
		super();
	}

	public int getGodina() {
		return godina;
	}

	public void setGodina(int godina) {
		this.godina = godina;
	}

	public Double getUkupanPorez() {
		return ukupanPorez;
	}

	public void setUkupanPorez(Double ukupanPorez) {
		this.ukupanPorez = ukupanPorez;
	}

	public boolean isZakljucena() {
		return zakljucena;
	}

	public void setZakljucena(boolean zakljucena) {
		this.zakljucena = zakljucena;
	}

	public Set<Faktura> getFaktureUGodini() {
		return faktureUGodini;
	}

	public void setFaktureUGodini(Set<Faktura> faktureUGodini) {
		this.faktureUGodini = faktureUGodini;
	}

	public Set<Predracun> getPredracuniUGodini() {
		return predracuniUGodini;
	}

	public void setPredracuniUGodini(Set<Predracun> predracuniUGodini) {
		this.predracuniUGodini = predracuniUGodini;
	}

	public Preduzece getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}
	
}
