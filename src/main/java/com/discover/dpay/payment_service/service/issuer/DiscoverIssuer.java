package com.discover.dpay.payment_service.service.issuer;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.discover.dpay.payment_service.entity.User;
import com.discover.dpay.payment_service.model.AccountRequest;
import com.discover.dpay.payment_service.model.CredentialData;
import com.discover.dpay.payment_service.model.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Service
public class DiscoverIssuer {

	private String domainDid = "3NYwx7Pfm1849J8joPX4CN";
	private String verityUrl = "https://vas.pps.evernym.com";
	private String xApiKey = "6BGk3CkFL2QGikz2iNKpaPXTcHM9h2oDMbVsPFvooazC:ejaiCdvos35Kmee7uHLhqjGkGGKQJHzE6sXNXVbVGfEchZz8Se37tzk5viSu79YBwZh1KQGVb5NzTutCF5CUwY8";
	private String credDefId = "CnZPjNwiKzcEpeGVk7fv2Z:3:CL:335531:latest";
	private String webhookUrl = "https://69c9-92-24-147-208.ngrok.io/webhook";
	private ObjectMapper mapper = new ObjectMapper();

	public void sendVerityRESTMessage(String qualifier, String msgFamily, String msgFamilyVersion, String msgName,
			Object object, String threadId) {
		String type = "did:sov:" + qualifier + ";spec/" + msgFamily + "/" + msgFamilyVersion + "/" + msgName;
		String id = UUID.randomUUID().toString();
		threadId = threadId == null ? UUID.randomUUID().toString() : threadId;


		String url = verityUrl + "/api/" + domainDid + "/" + msgFamily + "/" + msgFamilyVersion + "/" + threadId;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-API-key", xApiKey);
		    headers.setContentType(MediaType.APPLICATION_JSON); 

			HttpEntity<Object> entity = new HttpEntity<>(object,headers);
			AsyncRestTemplate  restTemplate = new AsyncRestTemplate();

			ListenableFuture<ResponseEntity<String>> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			response.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
		        @Override
		        public void onSuccess(ResponseEntity<String> result) {
		            System.out.println(result.getBody());
		        }

		        @Override
		        public void onFailure(Throwable ex) {
		        }
		    });

		} catch (Exception eek) {
			System.out.println("** Exception: " + eek.getMessage());
		}
	}
	
	public void updateWebHookEndpoint() {
		String webhookMessage = getJsonAsString("Webhookmessage.json");
		sendVerityRESTMessage("123456789abcdefghi1234", "configs", "0.6", "UPDATE_COM_METHOD", webhookMessage,null);
	}
	
	public void updateConfigMessage() {
		String updateConfigMessage = getJsonAsString("updateMessage.json");
		sendVerityRESTMessage("123456789abcdefghi1234", "update-configs", "0.6", "update", updateConfigMessage, null);

		}
	
	public void releationShipMessage() {
		String relationMessage = getJsonAsString("relationMessage.json");
		sendVerityRESTMessage("123456789abcdefghi1234", "relationship", "1.0", "create", relationMessage, null);
	}
	
	public void relationShipInvite() {
		String relationInviteMessage = getJsonAsString("relationInviteMessage.json");
		sendVerityRESTMessage("123456789abcdefghi1234", "relationship", "1.0", "out-of-band-invitation", relationInviteMessage, null);

	}

	private String getJsonAsString(String fileName) {
		InputStream is = DiscoverIssuer.class.getResourceAsStream("/"+fileName);
		return new BufferedReader(new InputStreamReader(is))
				   .lines().collect(Collectors.joining("\n"));
		
	}
	
    public BufferedImage generateQRCodeFromVerificationURL() throws WriterException, IOException {
    	InputStream is = DiscoverIssuer.class.getResourceAsStream("/verification.json");
		String result = new BufferedReader(new InputStreamReader(is))
				   .lines().collect(Collectors.joining("\n"));
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(result, BarcodeFormat.QR_CODE, 500, 500);
        ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", new File("C:\\Test\\output.png"));
		System.out.print(MatrixToImageWriter.toBufferedImage(bitMatrix));
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
    	
    }
    
    public void createCrentidals(AccountRequest accountRequest, Optional<User> user) throws JsonProcessingException {
    	Data data = new Data();
    	data.setCredDefId(credDefId);
    	data.setDomainDid(domainDid);
    	data.setVerityUrl(verityUrl);
    	data.setWebhookUrl(webhookUrl);
    	CredentialData cred = new CredentialData();
    	cred.setAccount_number(accountRequest.getAccountNumber());
    	cred.setFirst_name(user.get().getName());
    	cred.setLast_name(user.get().getName());
    	cred.setMobile_number(user.get().getMobileNumber());
        String json = mapper.writeValueAsString(data);
		sendVerityRESTMessage("BzCbsNYhMrjHiqZDTUASHg", "issue-credential", "1.0", "offer", json, null);

    }


}
