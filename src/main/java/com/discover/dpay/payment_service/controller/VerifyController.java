/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.discover.dpay.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discover.dpay.payment_service.service.VerfiyService;

@RestController
@RequestMapping("/v1/api")
public class VerifyController {

	@Autowired
	public final VerfiyService verifyService;

	public VerifyController(VerfiyService verifyService) {
		this.verifyService = verifyService;
	}

	@PostMapping("/verify")
	public ResponseEntity<String> ProcessPayment() {
		if(verifyService.verify()) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Success", HttpStatus.BAD_REQUEST);
	}
}
