package com.paymentservice.service;

import com.paymentservice.configuration.SharingFormula;
import com.paymentservice.entity.Contents;
import com.paymentservice.entity.Payments;
import com.paymentservice.payloads.request.PaymentsRequest;
import com.paymentservice.payloads.response.GenericServiceResponse;
import com.paymentservice.repository.ContentCreatorRepository;
import com.paymentservice.repository.ContentRepository;
import com.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PaymentService {
    public final ContentCreatorRepository contentCreatorRepository;
    public final ContentRepository contentRepository;
    public final PaymentRepository paymentRepository;


    public PaymentService(ContentCreatorRepository contentCreatorRepository, ContentRepository contentRepository, PaymentRepository paymentRepository){
        this.contentCreatorRepository = contentCreatorRepository;
        this.contentRepository = contentRepository;
        this.paymentRepository = paymentRepository;
    }

    public GenericServiceResponse makePaymentForContent(PaymentsRequest paymentsRequest){

        Optional<Contents> verifyIfContentExist = contentRepository.findActiveById(paymentsRequest.getContentId());
        if(!verifyIfContentExist.isPresent()){
            log.info("Content with title " + verifyIfContentExist.get().getTitle() + " was not found,aborting operation");
            return new GenericServiceResponse<>()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("This content  could not be found")
                    .responseBody(null);

        }
        else if(verifyIfContentExist.get().getContentAmount() > paymentsRequest.getAmountPaid()){
            log.info("Your account is insufficient, you need an extra " +
                    (verifyIfContentExist.get().getContentAmount() - paymentsRequest.getAmountPaid()) +
                    " To make a successful payment");
            return new GenericServiceResponse<>()
                    .responseCode(HttpStatus.EXPECTATION_FAILED)
                    .responseMessage("")
                    .responseBody(null);
        }

        log.info("Content with title " + verifyIfContentExist.get().getTitle() + " has been retrieved.....");
        log.info("Handshake between the client and the payment gateway successfully established.....");
        log.info("Successfully made payment to required wallets..logging details");

         Payments payment = new Payments();
        payment.setAmountPaid(paymentsRequest.getAmountPaid());
        payment.setContentId(verifyIfContentExist.get().getId());
        payment.setAmountPaidBy(paymentsRequest.getAmountPaidByFirstName() + " " + paymentsRequest.getAmountPaidByFirstName());
        payment.setContentPrice(verifyIfContentExist.get().getContentAmount());
        payment.setIsActive(true);
        payment.setAmountReceivedByClientInstitution(SharingFormula.calculateSharingFormula(10,verifyIfContentExist.get().getContentAmount()));
        log.info( payment.getAmountReceivedByClientInstitution() + " was paid into client Institutions wallet");
        payment.setAmountReceivedByContractingInstitution(SharingFormula.calculateSharingFormula(5,verifyIfContentExist.get().getContentAmount()));
        log.info( payment.getAmountReceivedByContractingInstitution() + " was paid into contracting Institutions wallet");
        payment.setAmountReceivedByContentCreator(SharingFormula.calculateSharingFormula(85,verifyIfContentExist.get().getContentAmount()));
        log.info( payment.getAmountReceivedByContentCreator() + " was paid into content creators  wallet");
        paymentRepository.save(payment);
        return new GenericServiceResponse()
                .responseCode(HttpStatus.CREATED)
                .responseMessage("Successfully created content creator")
                .responseBody(payment);

    }
}
