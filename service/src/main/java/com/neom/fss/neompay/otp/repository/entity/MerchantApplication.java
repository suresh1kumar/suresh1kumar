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
@Table(name = "merchant_application")
public class MerchantApplication {
    @Id
    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;
}
