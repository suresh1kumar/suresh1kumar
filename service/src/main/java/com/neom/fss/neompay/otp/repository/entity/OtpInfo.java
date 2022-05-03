package com.neom.fss.neompay.otp.repository.entity;

import com.neom.fss.neompay.otp.annotation.Generated;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Table(name = "otp_info")
public class OtpInfo implements Serializable {

    @Id
    @Column(name = "ref_id")
    private String refId;
    @Column(name = "user_identity")
    private String userIdentity;
    @Column(name = "otp_number")
    private Integer otpNumber;
    @Column(name = "generated_time")
    private LocalDateTime generatedTime;
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
    @Column(name = "confirmed_time")
    private LocalDateTime confirmedTime;
    @Column(name = "attempts_left")
    private Integer attemptsLeft;


}
