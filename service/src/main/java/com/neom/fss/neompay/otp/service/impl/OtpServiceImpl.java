package com.neom.fss.neompay.otp.service.impl;

import com.neom.fss.neompay.otp.model.*;
import com.neom.fss.neompay.otp.repository.*;
import com.neom.fss.neompay.otp.repository.entity.*;
import com.neom.fss.neompay.otp.service.OtpService;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Flogger
public class OtpServiceImpl implements OtpService {

    @Autowired
    OtpRepository otpRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BlockedCustomerRepository blockedCustomerRepository;
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    MerchantAppRepository merchantAppRepository;

    @Override
    public ResponseEntity<Object>  getOTP(String otpServiceCode,String mobileNo) throws NoSuchAlgorithmException {
        OtpResponse response;
            if(otpServiceCode.equalsIgnoreCase("signup_c")) {
              return customerSignUpOtp(mobileNo);
            }else if(otpServiceCode.equalsIgnoreCase("signup_m")){
               return merchantSignUpOtp(mobileNo);
            }
            else if(otpServiceCode.equalsIgnoreCase("login_reset_c")) {
                return customerLoginResetOtp(mobileNo);
            }else if(otpServiceCode.equalsIgnoreCase("merchant_app")){
                return merchantApplicationRetrieveOtp(mobileNo);
            }
            else{
                response=generateAndSaveOtp(mobileNo);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
    }

    private ResponseEntity<Object> customerSignUpOtp(String mobileNo) throws NoSuchAlgorithmException {
        OtpResponse response;
        BlockedCustomer blockedCustomer=blockedCustomerRepository.findByIdentifier(mobileNo);
        if(blockedCustomer!=null){
            if(LocalDateTime.now().isBefore(blockedCustomer.getBlockTime().plusMinutes(5))){
                response = OtpResponse.builder()
                        .statusCode(400)
                        .status(Boolean.FALSE)
                        .message(Constants.BLOCKED_CUSTOMER)
                        .reasonCode("BLOCKED_CUSTOMER")
                        .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            blockedCustomerRepository.delete(blockedCustomer);
        }
        Boolean userExist = findCustomerMobileNo(mobileNo);
        if (Boolean.FALSE.equals(userExist)) {
            response=generateAndSaveOtp(mobileNo);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response = OtpResponse.builder()
                    .statusCode(208)
                    .status(Boolean.FALSE)
                    .message(Constants.USER_ALREADY_EXIST)
                    .reasonCode("USER_ALREADY_EXIST")
                    .build();
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }
    }

    private ResponseEntity<Object> merchantSignUpOtp(String mobileNo) throws NoSuchAlgorithmException {
        OtpResponse response;
        Boolean userExist = findMerchantMobileNo(mobileNo);
        if (Boolean.FALSE.equals(userExist)) {
            response=generateAndSaveOtp(mobileNo);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response = OtpResponse.builder()
                    .statusCode(208)
                    .status(Boolean.FALSE)
                    .message(Constants.USER_ALREADY_EXIST)
                    .reasonCode("USER_ALREADY_EXIST")
                    .build();
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }
    }

    private ResponseEntity<Object> customerLoginResetOtp(String mobileNo) throws NoSuchAlgorithmException{
        OtpResponse response;
        Boolean userExist = findCustomerMobileNo(mobileNo);
        if (Boolean.TRUE.equals(userExist)) {
            response=generateAndSaveOtp(mobileNo);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response = OtpResponse.builder()
                    .statusCode(404)
                    .status(Boolean.FALSE)
                    .message(Constants.USER_NOT_AVAILABLE)
                    .reasonCode("USER_NOT_AVAILABLE")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private ResponseEntity<Object> merchantApplicationRetrieveOtp(String mobileNo) throws NoSuchAlgorithmException {
        OtpResponse response;
        Boolean userExist = findMerchantApplication(mobileNo);
        if (Boolean.TRUE.equals(userExist)) {
            response=generateAndSaveOtp(mobileNo);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response = OtpResponse.builder()
                    .statusCode(404)
                    .status(Boolean.FALSE)
                    .message(Constants.APPLICATION_NOT_FOUND+mobileNo)
                    .reasonCode("APPLICATION_NOT_FOUND")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private Boolean findMerchantApplication(String mobileNo) {
        MerchantApplication application=merchantAppRepository.findByMobileNo(mobileNo);
        return application != null;
    }

    private Boolean findCustomerMobileNo(String mobileNo) {
        Customer customer = customerRepository.findByMobileNo(mobileNo);
        return customer != null;

    }
    private Boolean findMerchantMobileNo(String mobileNo) {
        Merchant merchant = merchantRepository.findByMobileNo(mobileNo);
        return merchant != null;
    }

    @Override
    public ResponseEntity<Object> reSendOTP(String refId) throws NoSuchAlgorithmException {
            Optional<OtpInfo> entity = otpRepository.findById(refId);
            if (entity.isPresent()) {
                if (entity.get().getAttemptsLeft() == 0) {
                    OtpResponse response = new OtpResponse();
                    response.setStatus(Boolean.FALSE);
                    response.setErrorType("attemptsExceed");
                    response.setMessage(Constants.MAX_FAILED_ATTEMPS);
                    response.setReasonCode("MAX_FAILED_ATTEMPS");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                } else {
                    Integer otp = generateOTP();
                    Integer attemptsLeft = entity.get().getAttemptsLeft() - 1;
                    entity.get().setOtpNumber(otp);
                    entity.get().setGeneratedTime(LocalDateTime.now());
                    entity.get().setExpiryTime(LocalDateTime.now().plusMinutes(Constants.OTP_EXPIRED_TIME));
                    entity.get().setAttemptsLeft(attemptsLeft);
                    otpRepository.save(entity.get());
                    OtpResponse response = new OtpResponse();
                    response.setRefId(refId);
                    response.setOtpNumber(otp);
                    response.setAttemptsLeft(attemptsLeft);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            } else {
                OtpResponse response = new OtpResponse();
                response.setStatus(Boolean.FALSE);
                response.setMessage(Constants.INVALID_REF_ID);
                response.setReasonCode("INVALID_REF_ID");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
    }

    @Override
    public ResponseEntity<Object> validateOTP(ValidateOtpRequest request) {
        ValidateOtpResponse response = new ValidateOtpResponse();
            Optional<OtpInfo> entity = otpRepository.findById(request.getRefId());
            if (entity.isPresent()) {
                int requestOtp = Integer.parseInt(request.getOtpNumber());
                    if (entity.get().getOtpNumber() == requestOtp) {
                        LocalDateTime expiredAt = entity.get().getExpiryTime();
                        if (!expiredAt.isBefore(LocalDateTime.now()) && (entity.get().getConfirmedTime() == null)) {
                            response.setStatus(Boolean.TRUE);
                            response.setErrorType("");
                            response.setMessage(Constants.OTP_SUCCESS);
                            response.setReasonCode("OTP_SUCCESS");
                            entity.get().setConfirmedTime(LocalDateTime.now());
                            otpRepository.save(entity.get());
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } else {
                            response.setStatus(Boolean.FALSE);
                            response.setMessage(Constants.OTP_EXPIRED);
                            response.setMessage("OTP_EXPIRED");
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                        }
                    } else {
                        response.setStatus(Boolean.FALSE);
                        response.setErrorType("invalid");
                        response.setMessage(Constants.INVALID_OTP);
                        response.setReasonCode("INVALID_OTP");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
            } else {
               
                response.setStatus(Boolean.FALSE);
                response.setErrorType("");
                response.setMessage(Constants.INVALID_REF_ID);
                response.setReasonCode("INVALID_REF_ID");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

    }

    private OtpResponse generateAndSaveOtp(String mobileNo) throws NoSuchAlgorithmException {
        Integer otp = generateOTP();
        String refId = getUniqueId(mobileNo);
        OtpInfo entity =OtpInfo.builder()
                .refId(refId)
                .userIdentity(mobileNo)
                .otpNumber(otp)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3)
                .build();
        otpRepository.save(entity);
        return OtpResponse.builder()
                .refId(refId)
                .otpNumber(otp)
                .attemptsLeft(3)
                .build();
    }

    private Integer generateOTP() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return 100000 + random.nextInt(900000);
    }

    public String getUniqueId(String phoneNo) {
        Long someNumber = Long.parseLong(phoneNo);
        Long lastFive = someNumber % 100000;
        return System.currentTimeMillis() + "" + lastFive;
    }

}