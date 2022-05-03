package com.neom.fss.neompay.otp.repository.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "iban")
    private String iban;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "first_name")
    private String firstName;
}
