package com.discover.dpay.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
	
	private String verityUrl;
	
	private String domainDid;

	private String webhookUrl;

	private String credDefId;

	private CredentialData credentialData;

}
