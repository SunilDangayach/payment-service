package com.paymentservice.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.payloads.request.PaymentsRequest;
import com.paymentservice.payloads.response.CommonResponse;
import com.paymentservice.payloads.response.GenericServiceResponse;
import com.paymentservice.service.ContentCreatorService;
import com.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/v1/api")
public class PaymentController {

    public final PaymentService paymentService;


    public PaymentController(ContentCreatorService contentCreatorService, PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/ProcessPayment")
    public ResponseEntity<Object> ProcessPayment(@Valid @RequestBody PaymentsRequest paymentsRequest){
        GenericServiceResponse creator = paymentService.makePaymentForContent(paymentsRequest);
        return  CommonResponse.generateResponse(creator.responseCode(),
                creator.responseBody() == null ? false : true,
                creator.responseMessage(),
                creator.responseBody());
    }
}
