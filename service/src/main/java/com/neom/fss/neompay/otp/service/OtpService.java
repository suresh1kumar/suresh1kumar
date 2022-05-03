package com.neom.fss.neompay.otp.service;


import com.neom.fss.neompay.otp.model.ValidateOtpRequest;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;


public interface OtpService {

     ResponseEntity<Object> getOTP(String otpServiceCode,String mobileNo) throws NoSuchAlgorithmException;
     ResponseEntity<Object> reSendOTP(String refId) throws NoSuchAlgorithmException;
     ResponseEntity<Object> validateOTP(ValidateOtpRequest request);

}
