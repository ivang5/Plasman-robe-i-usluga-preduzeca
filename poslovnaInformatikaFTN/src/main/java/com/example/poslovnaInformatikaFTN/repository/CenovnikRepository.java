package com.example.poslovnaInformatikaFTN.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.poslovnaInformatikaFTN.entity.Cenovnik;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Integer>{	
	
	public Cenovnik findByDatumIzdavanja(Date datumIzdavanja);
	
	public List<Cenovnik> findAllByOrderByDatumIzdavanjaDesc();
	
}
