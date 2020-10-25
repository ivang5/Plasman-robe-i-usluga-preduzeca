package com.example.poslovnaInformatikaFTN.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poslovnaInformatikaFTN.entity.User;
import com.example.poslovnaInformatikaFTN.repository.UserRepository;

@Transactional
@Service
public class UserService implements UserServiceInterface{
	
	@Autowired
	UserRepository userRepository;
	@Override
	public User findOne(Integer id) {
		return userRepository.getOne(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	@Override
	public void remove(Integer id) {
		userRepository.deleteById(id);
	}

}
