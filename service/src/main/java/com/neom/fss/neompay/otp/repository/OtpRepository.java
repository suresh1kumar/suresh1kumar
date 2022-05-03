package com.neom.fss.neompay.otp.repository;

import com.neom.fss.neompay.otp.repository.entity.OtpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpInfo, String> {

        Optional<OtpInfo> findByUserIdentity(String mobileNo);


}
