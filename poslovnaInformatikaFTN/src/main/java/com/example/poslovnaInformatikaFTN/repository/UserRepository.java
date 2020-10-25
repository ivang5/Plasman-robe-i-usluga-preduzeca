package com.example.poslovnaInformatikaFTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.poslovnaInformatikaFTN.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	public User findByUsername(String username);
}
