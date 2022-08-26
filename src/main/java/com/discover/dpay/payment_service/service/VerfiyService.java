package com.discover.dpay.payment_service.service;

import org.springframework.stereotype.Service;

import com.discover.dpay.payment_service.service.verifier.App;

@Service
public class VerfiyService {

	
	public boolean verify() {
		try{
			new App().execute();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
}
