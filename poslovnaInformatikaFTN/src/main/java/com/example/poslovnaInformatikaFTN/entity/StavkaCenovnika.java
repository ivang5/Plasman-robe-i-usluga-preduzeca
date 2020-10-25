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
@Table(name="stavkeCenovnika")
public class StavkaCenovnika {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_stavkeCenovnika", unique=true, nullable=false)
	private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proizvoda", referencedColumnName = "id_proizvoda", nullable=false)
    private Proizvod proizvod;
    
	@Column(name="cena", unique=false, nullable=false)
	private Double cena;
	
	@ManyToOne
	@JoinColumn(name="id_cenovnika", referencedColumnName="id_cenovnika", nullable=false)
	private Cenovnik cenovnik;

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

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}
	
	
}
