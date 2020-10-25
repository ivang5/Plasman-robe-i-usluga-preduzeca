package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import com.example.poslovnaInformatikaFTN.entity.User;

public interface UserServiceInterface {
	
	public User findOne(Integer id);
	
	public User findByUsername(String username);
	
	public List<User> findAll();
	
	public User save(User user);
	
	public void remove(Integer id);

}
