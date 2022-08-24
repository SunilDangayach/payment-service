package com.discover.dpay.payment_service.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.discover.dpay.payment_service.entity.Account;
import com.discover.dpay.payment_service.repo.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


@Service
public class QRCodeService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public ResponseEntity<?> read(final MultipartFile file) throws IOException, NotFoundException {
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitmap);
		String accountNumber = new ObjectMapper().readValue(result.getText(), String.class);
		
		Account account = accountRepository.findOneByAccountNumber(accountNumber);
		
		if(account!=null) {
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failed",HttpStatus.UNAUTHORIZED);
	}

}
