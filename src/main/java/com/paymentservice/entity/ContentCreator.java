package com.paymentservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "contentCreator")
public class ContentCreator extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contentCreatorId",nullable = false)
    private Long id;

    @Column(name="firstName",nullable = false)
    private String firstName;

    @Column(name="lastName",nullable = false)
    private String lastName;

    @Column(name="email",nullable = true)
    @Email
    private String email;

    private Boolean isActive;

    @Column(name="phoneNumber",nullable = false)
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,mappedBy ="contentCreator")
    private Set<Contents> contents = new HashSet<>();

}
