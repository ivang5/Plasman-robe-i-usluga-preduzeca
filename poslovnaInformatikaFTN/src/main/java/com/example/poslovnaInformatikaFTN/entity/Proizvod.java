package com.example.poslovnaInformatikaFTN.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.poslovnaInformatikaFTN.dto.TipProizvodaDTO;

@Entity                 
@Table(name="proizvodi")
public class Proizvod {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_proizvoda", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="naziv", unique=false, nullable=false)
	private String naziv;
	
	@Column(name="jedinica_mere", unique=false, nullable=false)
	private String jedinicaMere;
	
	@Column(name="opis", unique=false, nullable=false)
	private String opis;
	
	@Column(name="tip_proizvoda", unique=false, nullable=false)
	private TipProizvodaDTO tipProizvoda;
	
	@Column(name="obrisan", unique=false, nullable=false)
	private boolean obrisan;
	
	@ManyToOne
	@JoinColumn(name="id_grupe", referencedColumnName="id_grupe_proizvoda", nullable=false)//treba false
	private GrupaProizvoda grupa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public TipProizvodaDTO getTipProizvoda() {
		return tipProizvoda;
	}

	public void setTipProizvoda(TipProizvodaDTO tipProizvoda) {
		this.tipProizvoda = tipProizvoda;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public GrupaProizvoda getGrupa() {
		return grupa;
	}

	public void setGrupa(GrupaProizvoda grupa) {
		this.grupa = grupa;
	}
	
}
