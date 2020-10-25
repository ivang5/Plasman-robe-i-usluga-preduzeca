package com.example.poslovnaInformatikaFTN.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.dto.FakturaDTO;
import com.example.poslovnaInformatikaFTN.dto.PredracunDTO;
import com.example.poslovnaInformatikaFTN.dto.PreduzeceDTO;
import com.example.poslovnaInformatikaFTN.dto.RacunOtpremnicaDTO;
import com.example.poslovnaInformatikaFTN.dto.StatusFaktureDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaFaktureDTO;
import com.example.poslovnaInformatikaFTN.dto.StavkaPredracunaDTO;
import com.example.poslovnaInformatikaFTN.entity.Faktura;
import com.example.poslovnaInformatikaFTN.entity.StavkaFakture;
import com.example.poslovnaInformatikaFTN.entity.Zaposleni;
import com.example.poslovnaInformatikaFTN.repository.CenovnikRepository;
import com.example.poslovnaInformatikaFTN.repository.FakturaRepository;
import com.example.poslovnaInformatikaFTN.repository.PoslovnaGodinaRepository;
import com.example.poslovnaInformatikaFTN.repository.PoslovniPartnerRepository;
import com.example.poslovnaInformatikaFTN.repository.PreduzeceRepository;
import com.example.poslovnaInformatikaFTN.repository.ProizvodRepository;
import com.example.poslovnaInformatikaFTN.repository.ZaposleniRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Transactional
@Service
public class FakturaService implements FakturaServiceInterface {
	
	@Autowired
	FakturaRepository fakturaRepository;
	
	@Autowired
	ZaposleniRepository zaposleniRepository;
	
	@Autowired
	ProizvodRepository proizvodRepository;
	
	@Autowired
	PoslovniPartnerRepository poslovniPartnerRepository;
	
	@Autowired
	PoslovnaGodinaRepository poslovnaGodinaRepository;
	
	@Autowired
	PreduzeceRepository preduzeceRepository;
	
	@Override
	public Faktura findOne(Integer id) {
		return fakturaRepository.getOne(id);
	}
	@Override
	public List<FakturaDTO> findAll() {
		List<FakturaDTO> faktureDTO = new ArrayList<FakturaDTO>();
		for(Faktura f : fakturaRepository.findAll()) {
			faktureDTO.add(new FakturaDTO(f));
		}
		return faktureDTO;
	}
	@Override
	public void save(FakturaDTO fakturaDTO) {
		Faktura faktura = new Faktura();
		faktura.setIzdavalac(zaposleniRepository.getOne(fakturaDTO.getIdIdavaoca()));
		Set<StavkaFakture> stavkeFakture = new HashSet<StavkaFakture>();
		for(StavkaFaktureDTO stavkaFaktureDTO : fakturaDTO.getStavkeFakture()) {
			StavkaFakture stavka = new StavkaFakture();
			stavka.setProizvod(proizvodRepository.getOne(stavkaFaktureDTO.getProizvod().getId()));
			stavka.setCena(stavkaFaktureDTO.getCena());
			stavka.setIznos(stavkaFaktureDTO.getIznos());
			stavka.setKolicina(stavkaFaktureDTO.getKolicina());
			stavka.setPdv(stavkaFaktureDTO.getPdv());
			stavka.setRabat(stavkaFaktureDTO.getRabat());
			stavka.setFaktura(faktura);
			stavkeFakture.add(stavka);
		}
		faktura.setStavkeFakture(stavkeFakture);
		faktura.setDatumFakture(fakturaDTO.getDatumFakture());
		faktura.setDatumValute(fakturaDTO.getDatumValute());
		faktura.setBrojRacuna(fakturaDTO.getBrojRacuna());
		faktura.setIznos(fakturaDTO.getIznos());
		faktura.setPdv(fakturaDTO.getPdv());
		faktura.setUkupanRabat(fakturaDTO.getUkupanRabat());
		faktura.setStatus(fakturaDTO.getStatus());
		faktura.setKupac(poslovniPartnerRepository.getOne(fakturaDTO.getKupac().getId()));
		faktura.setPozivNaBroj(fakturaDTO.getPozivNaBroj());
		faktura.setGodina(poslovnaGodinaRepository.getOne(fakturaDTO.getPoslovnaGodina()));
		
		fakturaRepository.save(faktura);
	}
	
	@Override
	public void update(FakturaDTO fakturaDTO) {
		Faktura faktura = fakturaRepository.getOne(fakturaDTO.getId());
		faktura.setIzdavalac(zaposleniRepository.getOne(fakturaDTO.getIdIdavaoca()));
		Set<StavkaFakture> stavkeFakture = new HashSet<StavkaFakture>();
		for(StavkaFaktureDTO stavkaFaktureDTO : fakturaDTO.getStavkeFakture()) {
			StavkaFakture stavka = new StavkaFakture();
			stavka.setProizvod(proizvodRepository.getOne(stavkaFaktureDTO.getProizvod().getId()));
			stavka.setCena(stavkaFaktureDTO.getCena());
			stavka.setIznos(stavkaFaktureDTO.getIznos());
			stavka.setKolicina(stavkaFaktureDTO.getKolicina());
			stavka.setPdv(stavkaFaktureDTO.getPdv());
			stavka.setRabat(stavkaFaktureDTO.getRabat());
			stavka.setFaktura(faktura);
			stavkeFakture.add(stavka);
		}
		faktura.setStavkeFakture(stavkeFakture);
		faktura.setDatumFakture(fakturaDTO.getDatumFakture());
		faktura.setDatumValute(fakturaDTO.getDatumValute());
		faktura.setBrojRacuna(fakturaDTO.getBrojRacuna());
		faktura.setIznos(fakturaDTO.getIznos());
		faktura.setPdv(fakturaDTO.getPdv());
		faktura.setUkupanRabat(fakturaDTO.getUkupanRabat());
		faktura.setStatus(fakturaDTO.getStatus());
		faktura.setKupac(poslovniPartnerRepository.getOne(fakturaDTO.getKupac().getId()));
		faktura.setPozivNaBroj(fakturaDTO.getPozivNaBroj());
		faktura.setGodina(poslovnaGodinaRepository.getOne(fakturaDTO.getPoslovnaGodina()));
		
		fakturaRepository.save(faktura);
	}
	
	@Override
	public RacunOtpremnicaDTO generateRacunOtpremnica(FakturaDTO fakturaDTO) {
		RacunOtpremnicaDTO racunOtpremnicaDTO = new RacunOtpremnicaDTO();
		racunOtpremnicaDTO.setId(fakturaDTO.getId());
		racunOtpremnicaDTO.setStavkeRacuna(fakturaDTO.getStavkeFakture());
		racunOtpremnicaDTO.setDatumRacuna(fakturaDTO.getDatumFakture());
		racunOtpremnicaDTO.setBrojRacunaPlacanja(fakturaDTO.getBrojRacuna());
		racunOtpremnicaDTO.setIznos(fakturaDTO.getIznos());
		racunOtpremnicaDTO.setPdv(fakturaDTO.getPdv());
		racunOtpremnicaDTO.setPozivNaBroj(fakturaDTO.getPozivNaBroj());
		racunOtpremnicaDTO.setKupac(fakturaDTO.getKupac());	
		racunOtpremnicaDTO.setProdavac(new PreduzeceDTO(preduzeceRepository.getOne(1)));
		
		return racunOtpremnicaDTO;
	}
	
	@Override
	public void remove(Integer id) {
		fakturaRepository.deleteById(id);
	}
	@Override
	public List<FakturaDTO> getAllFakturaInDateRange(Date dateStart, Date dateEnd) {
		List<FakturaDTO> faktureDTO = new ArrayList<FakturaDTO>();
		for(Faktura f : fakturaRepository.findAll()) {
			if(f.getDatumFakture().after(dateStart) && f.getDatumFakture().before(dateEnd))
				faktureDTO.add(new FakturaDTO(f));
		}
		return faktureDTO;
	}
	
	@Override
	public byte[] generateRacunPdf(RacunOtpremnicaDTO racunOtpremnicaDTO) {
	      Document document = new Document(new Rectangle(595 , 842 ), 0, 0, 0, 0);
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      try
	      {
	        PdfWriter writer = PdfWriter.getInstance(document, baos);
	        StringBuilder sb = new StringBuilder();
	        String html = "";
	        sb.append("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
	        sb.append("<style>*{box-sizing: border-box;margin: 0;padding: 0;}body {font-family: \"Times New Roman\", Times, serif;width: 21cm;height: 29.7cm;padding:10px;}#naslov{margin-bottom: 70px;}");
	        sb.append(".column1 {float: left;width: 49%;}.column2 {float: left;width: 350px;}.racunInfo p{margin: 0;padding: 0;}#kupacInfo{margin-top: 20px;}");
	        sb.append("#tableHeader{margin-bottom: -10px;}#tableHeader p{font-size:18px;margin-top: 5px;margin-bottom: 5px;}");
	        sb.append(".preduzeceInfo{margin-bottom: -10px;padding-left: 180px;text-align: left;float: left;}.preduzeceInfoBold{margin-bottom: -10px;text-align: left;float: right;} p{margin: 0;padding: 0;}");
	        sb.append(".center{text-align: center;}.row:after {content: \"\";display: table;clear: both;}</style>");
	        sb.append("</head><body>");
	        sb.append("<div class=\"row\" style=\" padding-bottom: 20px;\"><div class=\"column1\"><h1>");
	        sb.append(racunOtpremnicaDTO.getProdavac().getNaziv());
	        sb.append("</h1>");
	        sb.append("<h3>23243 BOTOŠ, Toze Markovića 6 <br/>Tekući račun: ");
	        sb.append(racunOtpremnicaDTO.getBrojRacunaPlacanja());
	        sb.append("</h3><br/><h2 id=\"naslov\">Račun/otpremnica ");
	        sb.append(racunOtpremnicaDTO.getId());
	        sb.append("</h2><div class=\"column1 racunInfo\"><p>Datum računa</p><p>Mesto izdavanja</p><p>Datum prometa</p><p>Mesto prometa</p>");
	        sb.append("</div><div class=\"column1 racunInfo\"><p>");
	        sb.append(dateToString(racunOtpremnicaDTO.getDatumRacuna()));
	        sb.append("</p><p>BOTOŠ</p><p>");
	        sb.append(dateToString(racunOtpremnicaDTO.getDatumRacuna()));
	        sb.append("</p><p>BOTOŠ</p></div></div><div class=\"column2\"><div class=\"row\"><br/><div class=\"preduzeceInfo\"><p>");
	        sb.append(" Telefon &nbsp;</p><p> Matični broj &nbsp;</p><p> Delatnost &nbsp;</p><p> P I B &nbsp;</p></div><div class=\"preduzeceInfoBold\"><p><b>");
	        sb.append(racunOtpremnicaDTO.getProdavac().getTelefon());
	        sb.append("</b></p><p><b>20924764</b></p><p><b>0111</b></p><p><b>");
	        sb.append(racunOtpremnicaDTO.getProdavac().getPib());
	        sb.append("</b></p></div></div><div id=\"kupacInfo\" class=\"row\"><br/><h2 class=\"center\">");
	        sb.append(racunOtpremnicaDTO.getKupac().getNaziv());
	        sb.append("</h2><h3 class=\"center\">");
	        sb.append(racunOtpremnicaDTO.getKupac().getAdresa());
	        sb.append("<br/><br/>PIB: ");
	        sb.append(racunOtpremnicaDTO.getKupac().getPib());
	        sb.append("</h3></div></div></div><hr/><div id=\"tableHeader\" class=\"row\"><div class=\"column1\" style=\"width:47.58px;\"><p>Šifra</p></div>");
	        sb.append("<div class=\"column1\" style=\"width:237.9px;\"><p>Naziv</p></div><div class=\"column1\" style=\"width:39.65px;\"><p>JM</p></div><div class=\"column1\" style=\"width:79.3px;\">");
	        sb.append("<p>Količina</p></div><div class=\"column1\" style=\"width:103.46px;\"><p>Cena &nbsp;&nbsp;Rabat</p></div><div class=\"column1\" style=\"width:126.88px;\"><p>Osnovica i iznos</p>");
	        sb.append("</div><div class=\"column1\" style=\"width:55.51px;\"><p align=\"center\">PDV-a</p></div><div class=\"column1\" style=\"width:95.16px;\"><p align=\"right\">Za plaćanje</p>");
	        sb.append("</div></div><hr/>");
	        for(StavkaFaktureDTO stavka : racunOtpremnicaDTO.getStavkeRacuna()) {
	        	sb.append("<div class=\"row\"><div class=\"column1\" style=\"width:47.58px;\"><p style=\"font-size:15px; margin-top: 2px; margin-bottom: 2px;\">");
	        	sb.append(stavka.getId());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:237.9px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getProizvod().getNaziv());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:39.65px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getProizvod().getJedinicaMere());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:79.3px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getKolicina());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:55.51px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getCena());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:55.51px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getRabat());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:118.95px;\"><p style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(stavka.getIznos());sb.append("&nbsp;&nbsp;");sb.append(stavka.getPdv());
	        	sb.append("</p></div><div class=\"column1\" style=\"width:79.3px;\"><p align=\"center\" style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	double iznosPdv = Math.round(stavka.getIznos()*stavka.getPdv()/100);
	        	sb.append(iznosPdv);
	        	sb.append("</p></div><div class=\"column1\" style=\"width:79.3px;\"><p align=\"right\" style=\"font-size:15px; margin-top: 2px;margin-bottom: 2px;\">");
	        	sb.append(Math.round(stavka.getIznos()+iznosPdv));
	        	sb.append("</p></div></div>");
	        }
	        sb.append("<hr/>");
	        sb.append("<div class=\"row\" style=\"margin-top: -10px; margin-bottom: -10px;\"><div class=\"column1\" style=\"width:475.8px;\"><p align=\"center\" style=\"font-size:18px;\">");
	        sb.append("Prilikom plaćanja navesti poziv na broj: ");
	        sb.append(racunOtpremnicaDTO.getPozivNaBroj());
	        sb.append("</p></div><div class=\"column1\" style=\"width:158.6px;\"><p style=\"font-size:18px;\">Osnovica PDV-a <br/> PDV ");
	        sb.append(Math.round(racunOtpremnicaDTO.getIznos()/racunOtpremnicaDTO.getPdv()));
	        sb.append("% <br/></p><h3>ZA PLAĆANJE</h3><p></p></div><div class=\"column1\" style=\"width:158.6px;\"><p align=\"right\" style=\"font-size:18px;\">");
	        sb.append(racunOtpremnicaDTO.getIznos());sb.append("<br/>");sb.append(racunOtpremnicaDTO.getPdv());
	        sb.append("<br/></p><h3 align=\"right\">");
	        sb.append((double) Math.round(racunOtpremnicaDTO.getIznos()+racunOtpremnicaDTO.getPdv()));
	        sb.append("</h3><p></p></div></div><hr/>");
	        sb.append("<div class=\"row\" style=\"margin-top: -10px;\"><p align=\"center\" style=\"font-size:18px;\">U slučaju plaćanja posle roka zaračunavamo zakonsku zateznu kamatu.");
	        sb.append("<br/>Za eventualne sporove priznaje se nadležnost Privrednog suda u Zrenjaninu. </p></div><div class=\"row\" style=\"margin-top: 40px;\"><div class=\"column1\" style=\"width:475.8px;\">");
	        sb.append("<p align=\"center\" style=\"font-size:18px;\">Za kupca</p></div><div class=\"column1\" style=\"width:158.6px;\"><p style=\"font-size:18px;\">Za KETIN Z.Z.</p></div></div></body></html>");
	        html = sb.toString();
	        document.open();
	        InputStream is = new ByteArrayInputStream(html.getBytes("UTF8"));
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document,is,StandardCharsets.UTF_8);
	        document.close();
	        writer.close();
	        System.out.println("Document created");
	      } catch (Exception e){
	         e.printStackTrace();
	      }
	      
	      return baos.toByteArray();
	}
	
	@Override
	public Faktura generateFaktura(PredracunDTO predracunDTO, String pozivNaBroj) {
		Faktura faktura = new Faktura();
		faktura.setIzdavalac(zaposleniRepository.getOne(predracunDTO.getIdIzdavaoca()));
		Set<StavkaFakture> stavkeFakture = new HashSet<StavkaFakture>();
		for(StavkaPredracunaDTO stavkaFaktureDTO : predracunDTO.getStavkePredracuna()) {
			StavkaFakture stavka = new StavkaFakture();
			stavka.setProizvod(proizvodRepository.getOne(stavkaFaktureDTO.getProizvod().getId()));
			stavka.setCena(stavkaFaktureDTO.getCena());
			stavka.setIznos(stavkaFaktureDTO.getIznos());
			stavka.setKolicina(stavkaFaktureDTO.getKolicina());
			stavka.setPdv(stavkaFaktureDTO.getPdv());
			stavka.setRabat(stavkaFaktureDTO.getRabat());
			stavka.setFaktura(faktura);
			stavkeFakture.add(stavka);
		}
		faktura.setStavkeFakture(stavkeFakture);
		faktura.setDatumFakture(new Date());
		faktura.setDatumValute(new Date());
		faktura.setBrojRacuna(predracunDTO.getBrojRacuna());
		faktura.setIznos(predracunDTO.getIznos());
		faktura.setPdv(predracunDTO.getPdv());
		faktura.setUkupanRabat(predracunDTO.getUkupanRabat());
		faktura.setStatus(StatusFaktureDTO.FORMIRANJE);
		faktura.setKupac(poslovniPartnerRepository.getOne(predracunDTO.getKupac().getId()));
		faktura.setPozivNaBroj(pozivNaBroj);
		faktura.setGodina(poslovnaGodinaRepository.getOne(predracunDTO.getPoslovnaGodina()));
		
		fakturaRepository.save(faktura);
		
		return faktura;
	}
	
	private static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");  
        return dateFormat.format(date); 
	}

}
