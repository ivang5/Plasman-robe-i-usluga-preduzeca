package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.Faktura;
import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;

public class FakturaDTO implements Serializable{

	private Integer id;
	private Integer idIdavaoca;
	private List<StavkaFaktureDTO> stavkeFakture;
	private Date datumFakture;
	private Date datumValute;
	private String brojRacuna;
	private Double iznos;
	private Double pdv;
	private Double ukupanRabat;
	private StatusFaktureDTO status;
	private PoslovniPartnerDTO kupac;
	private String pozivNaBroj;
	private Integer poslovnaGodina;
	
	public FakturaDTO() {}
	
	public FakturaDTO(Faktura f) {
		this.id = f.getId();
		this.idIdavaoca = f.getIzdavalac().getId();
    	List<StavkaFaktureDTO> stavkeFaktureDTOs = new ArrayList<>();
    	for (StavkaFakture itStavkaFakture : f.getStavkeFakture()) {
    		stavkeFaktureDTOs.add(new StavkaFaktureDTO(itStavkaFakture));
		}
		this.stavkeFakture = stavkeFaktureDTOs;
		this.datumFakture = f.getDatumFakture();
		this.datumValute = f.getDatumValute();
		this.brojRacuna = f.getBrojRacuna();
		this.iznos = f.getIznos();
		this.pdv = f.getPdv();
		this.ukupanRabat = f.getUkupanRabat();
		this.status = StatusFaktureDTO.valueOf(f.getStatus().toString());
		this.kupac = new PoslovniPartnerDTO(f.getKupac());
		this.pozivNaBroj = f.getPozivNaBroj();
		this.poslovnaGodina = f.getGodina().getGodina();
	}

	public FakturaDTO(Integer id, Integer idIdavaoca, List<StavkaFaktureDTO> stavkeFakture, Date datumFakture,
			Date datumValute, String brojRacuna, Double iznos, Double pdv, Double ukupanRabat, StatusFaktureDTO status,
			PoslovniPartnerDTO kupac, String pozivNaBroj, Integer poslovnaGodina) {
		super();
		this.id = id;
		this.idIdavaoca = idIdavaoca;
		this.stavkeFakture = stavkeFakture;
		this.datumFakture = datumFakture;
		this.datumValute = datumValute;
		this.brojRacuna = brojRacuna;
		this.iznos = iznos;
		this.pdv = pdv;
		this.ukupanRabat = ukupanRabat;
		this.status = status;
		this.kupac = kupac;
		this.pozivNaBroj = pozivNaBroj;
		this.poslovnaGodina = poslovnaGodina;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdIdavaoca() {
		return idIdavaoca;
	}

	public void setIdIdavaoca(Integer idIdavaoca) {
		this.idIdavaoca = idIdavaoca;
	}

	public List<StavkaFaktureDTO> getStavkeFakture() {
		return stavkeFakture;
	}

	public void setStavkeFakture(List<StavkaFaktureDTO> stavkeFakture) {
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

	public PoslovniPartnerDTO getKupac() {
		return kupac;
	}

	public void setKupac(PoslovniPartnerDTO kupac) {
		this.kupac = kupac;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public Integer getPoslovnaGodina() {
		return poslovnaGodina;
	}

	public void setPoslovnaGodina(Integer poslovnaGodina) {
		this.poslovnaGodina = poslovnaGodina;
	}
}
