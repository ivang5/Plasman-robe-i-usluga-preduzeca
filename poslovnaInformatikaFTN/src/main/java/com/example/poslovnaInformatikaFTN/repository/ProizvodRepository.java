package com.example.poslovnaInformatikaFTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.poslovnaInformatikaFTN.entity.Proizvod;

public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {

}
