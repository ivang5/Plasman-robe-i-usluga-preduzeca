package com.example.poslovnaInformatikaFTN.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.poslovnaInformatikaFTN.entity.PoslovniPartner;
import com.example.poslovnaInformatikaFTN.entity.RacunUBanci;

public class PoslovniPartnerDTO implements Serializable{

	private Integer id;
	private int pib;
	private String naziv;
	private VrstaPartneraDTO vrstaPartnera;
	private List<RacunUBanciDTO> racuniUBanci;
	private String adresa;
	private String telefon;
	private String email;

	public PoslovniPartnerDTO() {
		super();
	}
	
	public PoslovniPartnerDTO(PoslovniPartner p) {
		this.id = p.getId();
		this.pib = p.getPib();
		this.naziv = p.getNaziv();
		this.vrstaPartnera = VrstaPartneraDTO.valueOf(p.getVrstaPartnera().toString());
    	List<RacunUBanciDTO> racuniUBanciDTO = new ArrayList<>();
    	for (RacunUBanci itRacunUBanci : p.getRacuniUBanci()) {
    		if(!itRacunUBanci.isObrisan())
    			racuniUBanciDTO.add(new RacunUBanciDTO(itRacunUBanci));
		}
		this.racuniUBanci = racuniUBanciDTO;
		this.adresa = p.getAdresa();
		this.telefon = p.getTelefon();
		this.email = p.getEmail();
	}

	public PoslovniPartnerDTO(Integer id, int pib, String naziv, VrstaPartneraDTO vrstaPartnera,
			List<RacunUBanciDTO> racuniUBanci, String adresa, String telefon, String email) {
		super();
		this.id = id;
		this.pib = pib;
		this.naziv = naziv;
		this.vrstaPartnera = vrstaPartnera;
		this.racuniUBanci = racuniUBanci;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPib() {
		return pib;
	}

	public void setPib(int pib) {
		this.pib = pib;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public VrstaPartneraDTO getVrstaPartnera() {
		return vrstaPartnera;
	}

	public void setVrstaPartnera(VrstaPartneraDTO vrstaPartnera) {
		this.vrstaPartnera = vrstaPartnera;
	}

	public List<RacunUBanciDTO> getRacuniUBanci() {
		return racuniUBanci;
	}

	public void setRacuniUBanci(List<RacunUBanciDTO> racuniUBanci) {
		this.racuniUBanci = racuniUBanci;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
