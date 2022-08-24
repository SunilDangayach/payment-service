package com.paymentservice.payloads.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ContentCreatorRequest {

    @NotEmpty(message="FirstName cannot be empty")
    @NotBlank
    private String firstName;

    @NotEmpty(message="LastName cannot be empty")
    @NotBlank
    private String lastName;

    @NotEmpty(message="Email cannot be empty")
    @NotBlank
    private String email;

    @NotEmpty(message="PhoneNumber cannot be empty")
    @NotBlank
    private String phoneNumber;

}
