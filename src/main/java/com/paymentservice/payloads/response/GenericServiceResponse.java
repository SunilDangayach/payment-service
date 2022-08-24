package com.paymentservice.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true,fluent = true)
public class GenericServiceResponse <T> {
    private HttpStatus responseCode;
    private String responseMessage;
    private T responseBody;
}
