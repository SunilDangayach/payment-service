package com.discover.dpay.payment_service.data.jpa.service;

import com.discover.dpay.payment_service.service.issuer.DiscoverIssuer;

public class IssuerTesting {

	public static void main(String args[]) {
		DiscoverIssuer issuer = new DiscoverIssuer();
		issuer.updateWebHookEndpoint();
		issuer.updateConfigMessage();
		issuer.releationShipMessage();
		issuer.relationShipInvite();
	}

}
