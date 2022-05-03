package com.neom.fss.neompay.otp.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Table(name = "blocked_customers")
public class BlockedCustomer {

    @Id
    @Column(name = "user_identifier")
    private String userIdentifier;
    @Column(name = "block_time")
    private LocalDateTime blockTime;

}
