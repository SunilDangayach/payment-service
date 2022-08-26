package com.discover.dpay.payment_service.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.discover.dpay.payment_service.entity.Account;
import com.discover.dpay.payment_service.model.AccountRequest;
import com.discover.dpay.payment_service.service.AccountService;
import com.discover.dpay.payment_service.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/v1/api")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private QRCodeService qrCodeService;
	
	@PostMapping(path="/user/{userid}/account", produces = MediaType.IMAGE_PNG_VALUE)
	public BufferedImage createAccount(@Valid @RequestBody AccountRequest accountRequest, @PathParam(value="userid") Long userid) throws WriterException, IOException {
		return accountService.saveAccount(userid,accountRequest);
		
	}

	@PostMapping(path = "/account/{account_id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
	public BufferedImage generateQRCodeImage(@PathParam(value="account_id") Long accountId) throws Exception {
		Account account = accountService.findOneById(accountId);	
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(account.getAccountNumber(), BarcodeFormat.QR_CODE, 250, 250);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	
	@PutMapping(value = "/read", consumes = "multipart/form-data")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "returns decoded information inside provided QR code")
	public ResponseEntity<?> read(
			@Parameter(description = ".png image of QR code generated through this portal") @RequestParam(value = "file", required = true) MultipartFile file)
			throws IOException, NotFoundException {
		return qrCodeService.read(file);
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts(){
		List<Account> accounts= accountService.getAllAccount();
		return new ResponseEntity<List<Account>>(accounts,HttpStatus.OK);

	}
}
