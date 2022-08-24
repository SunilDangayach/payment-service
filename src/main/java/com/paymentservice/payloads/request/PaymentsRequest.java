package com.paymentservice.payloads.request;


import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PaymentsRequest {

    @NotEmpty(message = "FirstName cannot be empty")
    @NotBlank
    private String amountPaidByFirstName;

    @NotEmpty(message = "LastName cannot be empty")
    @NotBlank
    private String amountPaidByLastName;

    private Long contentId;

    private Double amountPaid;


}
