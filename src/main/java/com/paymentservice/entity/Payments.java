package com.paymentservice.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "payments")
public class Payments extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="paymentId",nullable = false)
    private Long id;

    @Column(name="amountPaidBy",nullable = false)
    private String amountPaidBy;

    @Column(name="contentId",nullable = false)
    private Long contentId;

    @Column(name="contentPrice",nullable = false)
    private Double contentPrice;

    @Column(name="amountPaid",nullable = false)
    private Double amountPaid;

    private Boolean isActive;

    @Column(name="amountPaidToClientInstitution",nullable = false)
    private Double amountReceivedByClientInstitution;

    @Column(name="amountPaidToContractingInstitution",nullable = false)
    private Double amountReceivedByContractingInstitution;

    @Column(name="amountPaidToContentCreator",nullable = false)
    private Double amountReceivedByContentCreator;


}
