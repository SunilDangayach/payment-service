package com.discover.dpay.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialData {
	
	private String mobile_number;

	private String first_name;

	private String last_name;

	private String account_number;


}
