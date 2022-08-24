package com.discover.dpay.payment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discover.dpay.payment_service.entity.User;
import com.discover.dpay.payment_service.model.UserRequest;
import com.discover.dpay.payment_service.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public User saveUser(UserRequest userRequest) {
		return userRepo.save(getUser(userRequest));

	}

	private User getUser(UserRequest userRequest) {
		return User.builder().name(userRequest.getUserName())
				.mobileNumber(userRequest.getMobileNumber())
				.build();
				
	}
	
	public List<User> getAllUser(){
		 return userRepo.findAll();
	}

	public User findOneById(long id){
		 return userRepo.getById(id);
	}
}
