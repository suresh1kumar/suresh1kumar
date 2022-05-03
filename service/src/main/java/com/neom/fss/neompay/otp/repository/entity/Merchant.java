package com.neom.fss.neompay.otp.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Table(name = "merchant")
public class Merchant {
    @Id
    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;

}
