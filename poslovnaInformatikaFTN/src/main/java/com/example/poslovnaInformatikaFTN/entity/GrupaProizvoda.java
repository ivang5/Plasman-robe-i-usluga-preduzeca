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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity                 
@Table(name="grupe_proizvoda")
public class GrupaProizvoda {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_grupe_proizvoda", unique=true, nullable=false)
	private Integer id;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="grupa")
	private Set<Proizvod> proizvodi = new HashSet<Proizvod>();
	
	@Column(name="naziv", unique=true, nullable=false)
	private String naziv;
	
	@ManyToOne
	@JoinColumn(name="id_preduzeca", referencedColumnName="id_preduzeca", nullable=false)
	private Preduzece preduzece;
	
	@ManyToOne
	@JoinColumn(name="id_pdv_kategorije", referencedColumnName="id_pdv_kategorije", nullable=false)
	private PDVKategorija pdvKategorija;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;

	public Integer getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Proizvod> getProizvodi() {
		return proizvodi;
	}

	public void setProizvodi(Set<Proizvod> proizvodi) {
		this.proizvodi = proizvodi;
	}

	public Preduzece getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
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
