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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity                 
@Table(name="cenovnik")
public class Cenovnik {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id_cenovnika", unique=true, nullable=false)
	private Integer id;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="cenovnik")
	private Set<StavkaCenovnika> stavkeCenovnika = new HashSet<StavkaCenovnika>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datum_izdavanja", unique=false, nullable=false)
	private Date datumIzdavanja;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<StavkaCenovnika> getStavkeCenovnika() {
		return stavkeCenovnika;
	}

	public void setStavkeCenovnika(Set<StavkaCenovnika> stavkeCenovnika) {
		this.stavkeCenovnika = stavkeCenovnika;
	}

	public Date getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}
}
