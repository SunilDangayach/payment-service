package com.paymentservice.payloads.request;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentsRequest {

    @NotEmpty(message = "Title cannot be empty")
    @NotBlank
    public String title;

    @NotEmpty(message = "Description cannot be empty")
    @NotBlank
    public String description;

    @NotEmpty(message = "Content cannot be empty")
    @NotBlank
    public String content;

    public Double contentAmount;
}
