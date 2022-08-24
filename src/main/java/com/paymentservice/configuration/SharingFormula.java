package com.paymentservice.configuration;

public class SharingFormula {

    public static double calculateSharingFormula(double percentageValue,double amountPaid){
        return (percentageValue * amountPaid) / 100;
    }
}
