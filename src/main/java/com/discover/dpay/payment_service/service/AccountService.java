package com.discover.dpay.payment_service.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discover.dpay.payment_service.entity.Account;
import com.discover.dpay.payment_service.entity.User;
import com.discover.dpay.payment_service.model.AccountRequest;
import com.discover.dpay.payment_service.model.AccountResponse;
import com.discover.dpay.payment_service.repo.AccountRepository;
import com.discover.dpay.payment_service.repo.UserRepository;
import com.discover.dpay.payment_service.service.issuer.DiscoverIssuer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DiscoverIssuer discoverIssuer;

	private Account getAccount(AccountRequest accountRequest, User user) {
		return Account.builder().accountNumber(accountRequest.getAccountNumber()).user_id(user.getUserId()).build();
	}
	
	public List<Account> getAllAccount(){
		 return accountRepo.findAll();
	}

	public Account findOneById(long id){
		 return accountRepo.getById(id);
	}

	public BufferedImage saveAccount(long userId, AccountRequest accountRequest) throws WriterException, IOException {
		Optional<User> user = userRepo.findById(userId);
		discoverIssuer.updateWebHookEndpoint();
		discoverIssuer.updateConfigMessage();
		discoverIssuer.releationShipMessage();
		discoverIssuer.relationShipInvite();
		discoverIssuer.createCrentidals(accountRequest,user);
		
		return discoverIssuer.generateQRCodeFromVerificationURL();
	}


}

