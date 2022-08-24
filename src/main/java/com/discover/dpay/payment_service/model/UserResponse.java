package com.discover.dpay.payment_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
	private long id;
	
	private String userName;
	
	private long mobileNumber;
}
